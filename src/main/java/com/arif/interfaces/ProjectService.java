package com.arif.interfaces;

import java.util.List;

import org.apache.log4j.Logger;

import com.arif.exception.ScrumBoardException;
import com.arif.model.Project;
import com.fdu.response.ScrumBoardResponse;

public interface ProjectService {
	
	final static Logger LOGGER = Logger.getLogger(ProjectService.class);

	default ScrumBoardResponse<Void> addProject(String projectName, String associateId, String token) {
		ScrumBoardResponse<Void> response = null;
		// 1. validate the input
		try {
			LOGGER.debug("validating input "+projectName);
			validateInput(projectName);
			LOGGER.debug("Input validated - OK");
		} catch (ScrumBoardException e) {
			LOGGER.error("User Input Not Valid ", e);
			// construct message with error details
			response = new ScrumBoardResponse<>();
			response.setCode(404);
			response.setMessage("Project Name cannot contain < > \" ! \' : { } ] characters");
			return response;
		}
		// 2. check if project exists already
		if(isProjectExists(projectName)) {
			LOGGER.info("Project - "+projectName+" - already exists");
			response = new ScrumBoardResponse<>();
			response.setCode(404);
			response.setMessage("Project already exists");
			return response;
		}
		// 3. add project
		LOGGER.debug("Proceeding with saving the given project");
		addProject(projectName);
		response = new ScrumBoardResponse<>();
		response.setCode(200);
		response.setMessage("Project Saved");
		return response;
	}

	void validateInput(String projectName) throws ScrumBoardException;

	boolean isProjectExists(String projectName);

	void addProject(String projectName);

	List<Project> getAllProjects();
}
