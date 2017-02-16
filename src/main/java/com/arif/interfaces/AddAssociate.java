package com.arif.interfaces;

import com.arif.model.Associate;
import com.fdu.response.ScrumBoardResponse;

public interface AddAssociate {

	default ScrumBoardResponse<?> addAssociate(Associate associate) {
		ScrumBoardResponse<?> response = new ScrumBoardResponse<>();
		// 1. check if associate exists in the system
		if (!isAssociateExists(associate)) {
			// If associates does not exist, do the following
			// 2. index record for fast search
			index(associate);
			// 3. add associate to the system
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
