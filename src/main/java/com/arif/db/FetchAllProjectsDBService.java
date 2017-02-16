package com.arif.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.codehaus.jackson.map.ObjectMapper;

import com.arif.model.Project;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class FetchAllProjectsDBService {

	MongoDatabase database;

	public FetchAllProjectsDBService(MongoDatabase database) {
		super();
		this.database = database;
	}

	/**
	 * retrieves all projects from database
	 * 
	 * @return {@link List} of {@link Project}
	 */
	public List<Project> fetchAllProjects() {

		List<Project> projectList = new ArrayList<>();

		MongoCollection<Document> associatesCollection = database.getCollection("projects");
		Block<Document> processRetreivedData = (document) -> {

			String retrivedDataAsJSON = document.toJson();
			Project project;
			try {
				project = new ObjectMapper().readValue(retrivedDataAsJSON, Project.class);
				projectList.add(project);
			} catch (IOException e) {
				e.printStackTrace();
			}

		};
		associatesCollection.find().forEach(processRetreivedData);

		return projectList;
	}
}
