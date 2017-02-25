package com.arif.interfaces;

import java.util.List;

import org.apache.log4j.Logger;

import com.arif.exception.ScrumBoardException;
import com.arif.model.ProjectNotes;
import com.fdu.response.ScrumBoardResponse;

public interface ProjectNotesService {
	
	final static Logger LOGGER = Logger.getLogger(ProjectNotesService.class);
	
	default ScrumBoardResponse<Void> saveNewProjectNotes(ProjectNotes projectNotes, String projectName, String associateId) {
		ScrumBoardResponse<Void> response = new ScrumBoardResponse<>();
		// 1. validate the input
		try {
			LOGGER.debug("Validating input for add new project notes operation");
			validateInput(projectNotes);
			LOGGER.debug("Input validated - OK");
		} catch (ScrumBoardException e) {
			LOGGER.error("User is attempting to save notes on a project that he does not have access to ", e);
			// construct message with error details
			response.setCode(403);
			response.setMessage("You do not have access to the project your are trying to save. Please contact your project/team lead");
			return response;
		}
		// 2. save project notes
		saveProjectNotes(projectNotes, projectName);
		LOGGER.debug("New project note successfully added for the project "+projectName);
		// send success response
		response.setCode(200);
		response.setMessage("Notes added to the project - projectName");
		return response;
	}

	List<ProjectNotes> getAllProjectNotes(String projectName);

	void saveProjectNotes(ProjectNotes projectNotes, String projectName);

	void validateInput(ProjectNotes projectNotes) throws ScrumBoardException;
}
