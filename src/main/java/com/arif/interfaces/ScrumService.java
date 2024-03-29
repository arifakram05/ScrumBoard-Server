package com.arif.interfaces;

import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;

import com.arif.exception.ScrumBoardException;
import com.arif.model.Project;
import com.arif.model.Scrum;
import com.arif.model.ScrumDetails;
import com.arif.response.ScrumBoardResponse;

/**
 * Handles services related to Scrum
 * 
 * @author arifakrammohammed
 *
 */
public interface ScrumService {

	final static Logger LOGGER = Logger.getLogger(ScrumService.class);

	/**
	 * performs validations on user input and adds a new scrum record for the
	 * specified project
	 * 
	 * @param scrum
	 *            scrum details to add
	 * @return a {@link ScrumBoardResponse} containing operation status
	 * @throws ParseException
	 *             if something goes wrong with date manipulation
	 */
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
			response.setMessage(e.getMessage());
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
		response.setMessage("Scrum already exists for given start and end dates. Please verify and re-submit");
		return response;
	}

	/**
	 * check if start date is less than end date<br/>
	 * check if given scrum name does not contain special characters
	 * 
	 * @param scrum
	 *            scrum details to add
	 * @throws ScrumBoardException
	 *             if user input is not per set rules
	 * @throws ParseException
	 *             if given dates are invalid
	 */
	void validateInput(Scrum scrum) throws ScrumBoardException, ParseException;

	/**
	 * check if Scrum already exists during given dates
	 * 
	 * @param scrum
	 *            Scrum to check
	 * @return <i>true</i> if scrum already exists on given dates
	 */
	boolean isScrumExists(Scrum scrum);

	/**
	 * Add Scrum to a project
	 * 
	 * @param scrum
	 *            scrum to add
	 * @throws ParseException
	 *             if date processing fails
	 */
	void authorizeScrum(Scrum scrum) throws ParseException;

	/**
	 * Create Scrum details
	 * 
	 * @param projectName
	 *            name of the project
	 * @return {@link List} of {@link ScrumDetails}
	 */
	List<ScrumDetails> createScrumDetails(String projectName);

	/**
	 * Get Scrum details on the specified date
	 * 
	 * @param scrumDate
	 *            Scrum date
	 * @param projectList
	 *            list of projects
	 * @return {@link List} of {@link Scrum}
	 */
	List<Scrum> getScrumDetails(String scrumDate, List<Project> projectList);

	/**
	 * Save daily Scrum update of an associate
	 * 
	 * @param scrumDetails
	 *            scrum details of given date
	 * @param date
	 *            date this record is be save against
	 * @param projectName
	 *            name of the project
	 */
	void saveDailyScrumUpdate(ScrumDetails scrumDetails, String date, String projectName);

	/**
	 * Get filtered Scrum details on the specified date and for specified
	 * project
	 * 
	 * @param scrumDate
	 *            date of scrum
	 * @param projectName
	 *            name of the project
	 * @return {@link List} of {@link Scrum}
	 */
	List<Scrum> getFilteredScrumDetails(String scrumDate, String projectName);

	/**
	 * Get the most recent scrum record for the given project
	 * 
	 * @param projectName
	 *            name of the project
	 * @return {@link List} of {@link Scrum}
	 */
	List<Scrum> getRecentScrumRecord(String projectName);
}
