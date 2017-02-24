package com.arif.db;

import static com.mongodb.client.model.Filters.eq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.codehaus.jackson.map.ObjectMapper;

import com.arif.exception.ScrumBoardException;
import com.arif.interfaces.ProjectService;
import com.arif.model.Project;
import com.fdu.constants.Constants;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ProjectServiceImpl implements ProjectService {

	final static Logger LOGGER = Logger.getLogger(ProjectServiceImpl.class);
	MongoDatabase database;

	public ProjectServiceImpl(MongoDatabase database) {
		super();
		this.database = database;
	}

	/**
	 * Insert a new project document into the project collection
	 * 
	 * @param projectName
	 * @return
	 */
	public void addProject(String projectName) {
		// get collection
		MongoCollection<Document> projectsCollection = database.getCollection(Constants.PROJECTS.getValue());
		// create document
		Document document = new Document();
		document.append(Constants.PROJECTNAME.getValue(), projectName.trim());
		document.append(Constants.PROJECTID.getValue(), null);//TODO : generate projectId value
		// save document
		projectsCollection.insertOne(document);
	}

	@Override
	public void validateInput(String projectName) throws ScrumBoardException {
		//anti XSS vulnerability
		//projectName = ESAPI.encoder().canonicalize(projectName);	
		if(Pattern.matches(Constants.SCRIPTTAGS.getValue(), projectName)) {
			throw new ScrumBoardException("Invalid input");
		}
		if(Pattern.matches(Constants.JAVASCRIPT.getValue(), projectName)) {
			throw new ScrumBoardException("Invalid input");
		}
	}

	@Override
	public List<Project> getAllProjects() {
		List<Project> projectList = new ArrayList<>();
		// get collection
		MongoCollection<Document> projectsCollection = database.getCollection(Constants.PROJECTS.getValue());
		// processed retrieved data
		Block<Document> processRetreivedData = (document) -> {

			String retrivedDataAsJSON = document.toJson();
			Project project;
			try {
				project = new ObjectMapper().readValue(retrivedDataAsJSON, Project.class);
				projectList.add(project);
			} catch (IOException e) {
				LOGGER.error("Error while processing retrieved projects ",e);
			}

		};
		// query
		projectsCollection.find().forEach(processRetreivedData);
		LOGGER.info("All projects fetched");
		return projectList;
	}

	@Override
	public boolean isProjectExists(String projectName) {
		// get collection
		MongoCollection<Document> projectsCollection = database.getCollection(Constants.PROJECTS.getValue());
		// query
		return projectsCollection.count(eq(Constants.PROJECTNAME.getValue(), projectName.trim())) != 0 ? true : false;
	}
}
