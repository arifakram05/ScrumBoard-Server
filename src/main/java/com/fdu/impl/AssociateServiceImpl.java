package com.fdu.impl;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.arif.exception.ScrumBoardException;
import com.arif.interfaces.AssociateService;
import com.arif.model.Associate;
import com.arif.model.Project;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class AssociateServiceImpl implements AssociateService {

	MongoDatabase database;

	public AssociateServiceImpl(MongoDatabase database) {
		super();
		this.database = database;
	}

	@Override
	public void index(Associate associate) {
		// code to index associate details
	}

	@Override
	public boolean isAssociateExists(Associate associate) {
		// get collection
		MongoCollection<Document> associatesCollection = database.getCollection("associates");
		// query collection
		long resultsCount = associatesCollection.count(eq("associateId", associate.getAssociateId()));
		if (resultsCount != 0)
			return true;
		else
			return false;
	}

	@Override
	public void authorizeAssociate(Associate associate) {
		// get collection
		MongoCollection<Document> projectsCollection = database.getCollection("associates");
		// create document
		Document document = new Document();
		// add document properties
		document.put("associateName", associate.getAssociateName());
		document.put("associateId", associate.getAssociateId());
		document.put("role", associate.getRole());

		/*
		 * In order to insert List of Objects, you need to first add BSON
		 * behavior to each of those objects, and only then can you add
		 * the array to the document being inserted.
		 */
		// create a DBObject such that you can insert a list of Projects
		List<DBObject> projectList = new ArrayList<DBObject>();
		for (Project project : associate.getProjects()) {
			DBObject bsonProject = getBsonFromPojo(project);
			projectList.add(bsonProject);
		}
		document.put("projects", projectList);

		// save document
		projectsCollection.insertOne(document);
	}

	/**
	 * Add BSON behavior for the document
	 * 
	 * @param project
	 * @return
	 */
	//This needs go into Project class
	//Project ID needs to be generated
	private DBObject getBsonFromPojo(Project project) {
		BasicDBObject document = new BasicDBObject();

		document.put("projectName", project.getProjectName());
		document.put("projectId", "1");

		return document;
	}

	@Override
	public void validateInput(Associate associate) throws ScrumBoardException {
		// TODO Auto-generated method stub
		
	}

}
