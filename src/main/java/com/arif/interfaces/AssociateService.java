package com.arif.interfaces;

import org.apache.log4j.Logger;

import com.arif.exception.ScrumBoardException;
import com.arif.model.Associate;
import com.fdu.response.ScrumBoardResponse;

public interface AssociateService {

	final static Logger LOGGER = Logger.getLogger(AssociateService.class);

	default ScrumBoardResponse<Void> addAssociate(Associate associate) {
		ScrumBoardResponse<Void> response = new ScrumBoardResponse<>();
		// 1. validate the input
		try {
			LOGGER.debug("Validating input for add/udpate of an associate");
			validateInput(associate);
			LOGGER.debug("Input validated. Proceeding");
		} catch (ScrumBoardException e) {
			LOGGER.error("User Input Not Valid ", e);
			// construct message with error details
			response.setCode(404);
			response.setMessage("Associate details cannot contain special characters");
			return response;
		}
		// 2. check if associate does not exist
		if (!isAssociateExists(associate)) {
			LOGGER.info("Adding new associate - "+associate.getAssociateId());
			// If associates does not exist, do the following
			// 3. index record for fast search
			index(associate);
			// 4. add associate to the system
			authorizeAssociate(associate);
			// send success response
			response.setCode(200);
			response.setMessage("Associate Added Successfully");
			return response;
		}
		// as associate already exists, perform update
		// i.e. take all not-null details provided by the user, and update the details of given associate id
		// 3. Update associate
		LOGGER.info("Associate - "+associate.getAssociateId()+" - already exists, so proceeding with updating user details");
		updateAssociate(associate);
		response.setCode(200);
		response.setMessage("Associate already exists in the system. So, updated the associate with given details");
		return response;
	}

	void validateInput(Associate associate) throws ScrumBoardException;

	/**
	 * Check if an associate already exists in the database
	 * 
	 * @param associate
	 *            Associate to check
	 * @return
	 */
	boolean isAssociateExists(Associate associate);

	void index(Associate associate);

	/**
	 * Add associate to the database
	 * 
	 * @param associate
	 *            Associate to add
	 */
	void authorizeAssociate(Associate associate);

	/**
	 * Update associate
	 * 
	 * @param associate
	 *            Associate to update
	 */
	void updateAssociate(Associate associate);
}
