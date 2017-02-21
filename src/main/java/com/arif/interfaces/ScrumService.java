package com.arif.interfaces;

import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;

import com.arif.exception.ScrumBoardException;
import com.arif.model.Project;
import com.arif.model.Scrum;
import com.arif.model.ScrumDetails;
import com.fdu.response.ScrumBoardResponse;

public interface ScrumService {

	final static Logger LOGGER = Logger.getLogger(ScrumService.class);

	default ScrumBoardResponse<Void> addScrum(Scrum scrum) throws ParseException {
		ScrumBoardResponse<Void> response = new ScrumBoardResponse<>();
		// 1. validate the input
		try {
			LOGGER.debug("Validating input for add scrum operation");
			validateInput(scrum);
			LOGGER.debug("Input validated - OK");
		} catch (ScrumBoardException e) {
			LOGGER.error("User Input Not Valid ", e);
			// construct message with error details
			response.setCode(404);
			response.setMessage("End date should be greater than Start date");
			return response;
		}
		// 2. verify that no Scrum exists during the given dates
		if (!isScrumExists(scrum)) {
			// 3. create ScrumDetails list
			List<ScrumDetails> scrumDetailsList = createScrumDetails(scrum.getProjectName());
			// 4. add Scrum to the system
			scrum.setScrumDetails(scrumDetailsList);
			authorizeScrum(scrum);
			// send success response
			response.setCode(200);
			response.setMessage("Scrum Added Successfully");
			return response;
		}
		// if Scrum exists already
		response.setCode(404);
		response.setMessage(
				"Scrum already exists for given start and end dates. Please verify and re-submit");
		return response;
	}

	void validateInput(Scrum scrum) throws ScrumBoardException, ParseException;

	/**
	 * check if Scrum already exists during given dates
	 * 
	 * @param scrum
	 *            Scrum to check
	 * @return
	 */
	boolean isScrumExists(Scrum scrum);

	/**
	 * Add Scrum to the database
	 * 
	 * @param scrum
	 *            Scrum to add
	 */
	void authorizeScrum(Scrum scrum) throws ParseException;

	/**
	 * Create Scrum details
	 * 
	 * @param projectName
	 *            Name of the project
	 * @return
	 */
	List<ScrumDetails> createScrumDetails(String projectName);

	/**
	 * Get Scrum details on the specified date
	 * 
	 * @param scrumDate
	 *            Scrum date
	 * @param projectName
	 *            Project name
	 * @return
	 */
	List<Scrum> getScrumDetails(String scrumDate, List<Project> projectList);

	/**
	 * Save daily Scrum update of an associate
	 * 
	 * @param scrum
	 * @param associateId
	 * @param projectName
	 * @return
	 */
	void saveDailyScrumUpdate(ScrumDetails scrumDetails, String date, String projectName);
}
