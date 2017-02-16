package com.arif.db;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class AddProjectDBService {

	MongoDatabase database;

	public AddProjectDBService(MongoDatabase database) {
		super();
		this.database = database;
	}

	/**
	 * Insert a new project document into the project collection
	 * 
	 * @param projectName
	 * @return
	 */
	public boolean addProject(String projectName) {
		// get collection
		MongoCollection<Document> projectsCollection = database.getCollection("projects");
		// create document
		Document document = new Document("projectName", projectName);
		// save document
		projectsCollection.insertOne(document);

		return true;
	}
}
