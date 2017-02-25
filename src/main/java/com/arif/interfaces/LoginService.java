package com.arif.interfaces;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.arif.exception.ScrumBoardException;
import com.arif.model.Associate;
import com.arif.util.SecureLogin;
import com.fdu.response.ScrumBoardResponse;

public interface LoginService {
	
	final static Logger LOGGER = Logger.getLogger(LoginService.class);

	default ScrumBoardResponse<Associate> login(String associateId) throws NoSuchAlgorithmException {
		ScrumBoardResponse<Associate> response = new ScrumBoardResponse<>();
		//1. validate the input
		try {
			LOGGER.debug("Validating input for user login");
			validateInput(associateId);
			LOGGER.debug("Input validated - OK. Proceeding with login");
		} catch (ScrumBoardException e) {
			LOGGER.error("User Input Not Valid : "+ associateId, e);
			// construct message with error details
			response.setCode(404);
			response.setMessage("Login details can only contain numbers");
			return response;
		}
		//2. check if associate present in the system
		Associate associateDetails = getAssociateDetails(associateId);
		LOGGER.debug("Associate details retreived from database. Generating token");
		// 3. generate JWT Token
		if(associateDetails != null) {
			String authToken = SecureLogin.createJWT(associateId, -1);
			LOGGER.debug("JWT Token generated for Associate. User can login");
			// construct response with Associate details and JWT token
			response.setAuthToken(authToken);
			response.setCode(200);
			response.setMessage("Login Success");
			response.setResponse(Arrays.asList(associateDetails));
		} else {
			//if given login id is not present in the system
			LOGGER.debug("Login id not recognized by the system");
			// construct message with error details
			response.setCode(403);
			response.setMessage("Log-in Id not valid. Please check with your supervisor for access to the system");
		}
		
		return response;
	}
	
	void validateInput(String associateId) throws ScrumBoardException;
	
	Associate getAssociateDetails(String associateId);

}
