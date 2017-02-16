package com.fdu.impl;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.bson.Document;
import org.codehaus.jackson.map.ObjectMapper;

import com.arif.interfaces.ScrumOperations;
import com.arif.model.Scrum;
import com.arif.model.ScrumDetails;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ScrumOperationsImpl implements ScrumOperations {

	MongoDatabase database;

	public ScrumOperationsImpl(MongoDatabase database) {
		super();
		this.database = database;
	}

	@Override
	// TODO write implementation
	public boolean isScrumExists(Scrum scrum) {
		// get all dates between start date and end date, then query database
		// with each date to check if a Scrum exists already
		return false;
	}

	@Override
	public List<ScrumDetails> createScrumDetails(String projectName) {
		/*
		 * LOGIC : get all the ids and names of associates on a given project from associates
		 * collection, then form ScrumDetails with details of those associates
		 */
		// list to store details of associates that belong to given project
		List<ScrumDetails> scrumDetailsList = new ArrayList<>();
		// get collection
		MongoCollection<Document> associatesCollection = database.getCollection("associates");
		// process each retrieved record
		Block<Document> processRetreivedData = (document) -> {

			String retrivedDataAsJSON = document.toJson();
			ScrumDetails scrumDetails;
			try {
				scrumDetails = new ObjectMapper().readValue(retrivedDataAsJSON, ScrumDetails.class);
				scrumDetailsList.add(scrumDetails);
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
		
		/*
		 * This is the way of searching through the documents with array of objects.
		 * "projects" is the field with array of objects, and "projectName" is the one of fields in the object.
		 * Projection class tells to retrieve only the specified fields of matched documents.
		 * associatesCollection.find(eq("projects.projectName", "carecompass")).projection(fields(include("projects"), excludeId())).forEach(processRetreivedData);
		 */
		// query that fetched associate name and id who is associated with given project
		associatesCollection.find(eq("projects.projectName", projectName)).projection(fields(include("associateName", "associateId"), excludeId())).forEach(processRetreivedData);	

		return scrumDetailsList;
	}

	@Override
	public void authorizeScrum(Scrum scrum) throws ParseException {
		// get collection
		MongoCollection<Document> scrumCollection = database.getCollection(scrum.getProjectName());

		// get all dates between start date and end date, 
		// then save to database each Scrum on every single date
		List<String> scrumDays = getAllDatesBetweenTwoDates(scrum.getStartDate(), scrum.getEndDate());

		// insert one Document for each Scrum day
		for (String scurmDate : scrumDays) {
			// create document
			Document document = new Document();

			document.put("actualDate", scurmDate);
			document.put("startDate", scrum.getStartDate());
			document.put("endDate", scrum.getEndDate());
			document.put("scrumName", scrum.getScrumName());

			/*
			 * In order to insert List of Objects, you need to first add BSON
			 * behavior to each of those objects, and only then can you add the
			 * array to the document being inserted.
			 */
			// create a DBObject such that you can insert a list of ScrumDetails
			List<DBObject> scrumDetailsList = new ArrayList<DBObject>();
			for (ScrumDetails scrumDetails : scrum.getScrumDetails()) {
				DBObject bsonScrumDetails = getBsonFromPojo(scrumDetails);
				scrumDetailsList.add(bsonScrumDetails);
			}
			document.put("scrumDetails", scrumDetailsList);

			// save document
			scrumCollection.insertOne(document);
		}
	}

	@Override
	public List<Scrum> getScrumDetails(String scrumDate, String projectName) {
		// list to store details of associates that belong to given project
		List<Scrum> scrumDetailsList = new ArrayList<>();
		// get collection
		MongoCollection<Document> scrumDetailsCollection = database.getCollection(projectName);
		// process each retrieved record
		Block<Document> processRetreivedData = (document) -> {

			String retrivedDataAsJSON = document.toJson();
			Scrum scrumDetails;
			try {
				scrumDetails = new ObjectMapper().readValue(retrivedDataAsJSON, Scrum.class);
				scrumDetailsList.add(scrumDetails);
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
		// query
		scrumDetailsCollection.find(eq("actualDate", scrumDate)).projection(fields(include("scrumDetails"), excludeId())).forEach(processRetreivedData);
		
		return scrumDetailsList;
	}

	// TODO This method needs performance improvement
	private static List<String> getAllDatesBetweenTwoDates(String startDate, String endDate) throws ParseException {
		List<String> daysListAsStrings = new ArrayList<String>();

		// convert dates in String format to Date format
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM, yyyy", Locale.US);
		Date startDateParsed = simpleDateFormat.parse(startDate);
		Date endDateParsed = simpleDateFormat.parse(endDate);

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startDateParsed);

		// LocalDate dateStart =
		// startDateParsed.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		while (calendar.getTime().before(endDateParsed)) {
			Date date = calendar.getTime();
			String dateAsString = simpleDateFormat.format(date);
			daysListAsStrings.add(dateAsString);
			calendar.add(Calendar.DATE, 1);
		}

		/*
		 * List<LocalDate> dates = Stream.iterate(startDateParsed, date ->
		 * startDateParsed.plusDays(1)) .limit(ChronoUnit.DAYS.between(start,
		 * end)) .collect(Collectors.toList());
		 */

		return daysListAsStrings;
	}

	/**
	 * Add BSON behavior for the document
	 * 
	 * @param project
	 * @return
	 */
	// TODO This needs to go into ScrumDetails class
	private DBObject getBsonFromPojo(ScrumDetails scrumDetails) {
		BasicDBObject document = new BasicDBObject();

		document.put("associateId", scrumDetails.getAssociateId());
		document.put("associateName", scrumDetails.getAssociateName());
		document.put("yesterday", scrumDetails.getYesterday());
		document.put("today", scrumDetails.getToday());
		document.put("roadblocks", scrumDetails.getRoadblocks());

		return document;
	}	

}
