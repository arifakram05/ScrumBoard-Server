package com.arif.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.codehaus.jackson.map.ObjectMapper;

import com.arif.constants.Constants;
import com.arif.exception.ScrumBoardException;
import com.arif.interfaces.ProjectNotesService;
import com.arif.model.ProjectNotes;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * contains all the logic related to project notes
 * 
 * @author arifakrammohammed
 *
 */
public class ProjectNotesServiceImpl implements ProjectNotesService {

	final static Logger LOGGER = Logger.getLogger(ProjectNotesServiceImpl.class);
	MongoDatabase database;

	public ProjectNotesServiceImpl(MongoDatabase database) {
		super();
		this.database = database;
	}

	@Override
	public List<ProjectNotes> getAllProjectNotes(String projectName) {
		List<ProjectNotes> projectNotesList = new ArrayList<>();
		// get collection
		MongoCollection<Document> projectsCollection = database.getCollection(projectName + Constants.NOTES.getValue());
		// processed retrieved data
		Block<Document> processRetreivedData = (document) -> {

			String retrivedDataAsJSON = document.toJson();
			ProjectNotes projectNotes;
			try {
				projectNotes = new ObjectMapper().readValue(retrivedDataAsJSON, ProjectNotes.class);
				projectNotesList.add(projectNotes);
			} catch (IOException e) {
				LOGGER.error("Error while processing retrieved project notes ", e);
			}

		};
		// query
		projectsCollection.find().forEach(processRetreivedData);
		LOGGER.info("All project notes fetched");
		return projectNotesList;
	}

	@Override
	public void saveProjectNotes(ProjectNotes projectNotes, String projectName) {
		// get collection
		MongoCollection<Document> projectsNotesCollection = database
				.getCollection(projectName + Constants.NOTES.getValue());
		// create document
		Document document = new Document();
		// add document properties
		document.put(Constants.TITLE.getValue(), projectNotes.getTitle().trim());
		document.put(Constants.CREATEDON.getValue(), projectNotes.getCreatedOn());
		document.put(Constants.AUTHOR.getValue(), projectNotes.getAuthor());
		document.put(Constants.NOTESFIELD.getValue(), projectNotes.getNotes().trim());

		// save document
		projectsNotesCollection.insertOne(document);
	}

	@Override
	public void validateInput(ProjectNotes projectNotes) throws ScrumBoardException {
		// checking project notes title
		String title = projectNotes.getTitle();
		Pattern pattern = Pattern.compile(Constants.RESTRICT.getValue());
		Matcher matcher = pattern.matcher(title);
		while (matcher.find()) {
			throw new ScrumBoardException("Invalid input");
		}
	}

}
