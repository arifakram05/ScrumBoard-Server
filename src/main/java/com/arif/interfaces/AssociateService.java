package com.arif.interfaces;

import org.apache.log4j.Logger;

import com.arif.exception.ScrumBoardException;
import com.arif.model.Associate;
import com.fdu.response.ScrumBoardResponse;

public interface AssociateService {

	final static Logger LOGGER = Logger.getLogger(AssociateService.class);

	default ScrumBoardResponse<?> addAssociate(Associate associate) {
		ScrumBoardResponse<?> response = new ScrumBoardResponse<>();
		// 1. validate the input
		try {
			validateInput(associate);
			LOGGER.debug("Input validated - OK");
		} catch (ScrumBoardException e) {
			LOGGER.error("User Input Not Valid ", e);
			// construct message with error details
			response.setCode(404);
			response.setMessage("Associate details cannot contain special characters");
			return response;
		}
		// 2. check if associate exists in the system
		if (!isAssociateExists(associate)) {
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
		// if associate exists already
		response.setCode(404);
		response.setMessage("Associate already exists in the system. You can proceed to  project assignment");
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

}
