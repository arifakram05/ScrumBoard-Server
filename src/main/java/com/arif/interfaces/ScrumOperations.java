package com.arif.interfaces;

import java.text.ParseException;
import java.util.List;

import com.arif.model.Scrum;
import com.arif.model.ScrumDetails;
import com.fdu.response.ScrumBoardResponse;

public interface ScrumOperations {

	default ScrumBoardResponse<?> addScrum(Scrum scrum) throws ParseException {
		ScrumBoardResponse<?> response = new ScrumBoardResponse<>();
		// 1. verify that no Scrum exists during the given dates
		if (!isScrumExists(scrum)) {
			// 2. create ScrumDetails list
			List<ScrumDetails> scrumDetailsList = createScrumDetails(scrum.getProjectName());
			// 3. add Scrum to the system
			scrum.setScrumDetails(scrumDetailsList);
			authorizeScrum(scrum);
			// send success response
			response.setCode(200);
			response.setMessage("Scrum Added Successfully");
			return response;
		}
		// if associate exists already
		response.setCode(404);
		response.setMessage(
				"Scrum already exists for one or more given dates. You can only have one Scrum associated with any given date. Please verify and re-submit");
		return response;
	}

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
	List<Scrum> getScrumDetails(String scrumDate, String projectName);
}
