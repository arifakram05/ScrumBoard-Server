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
			validateInput(associateId);
			LOGGER.debug("Input validated - OK");
		} catch (ScrumBoardException e) {
			LOGGER.error("User Input Not Valid ", e);
			// construct message with error details
			response.setCode(404);
			response.setMessage("Login details cannot contain special characters");
			return response;
		}
		//2. check if associate present in the system
		Associate associateDetails = getAssociateDetails(associateId);
		LOGGER.debug("Associate details retreived from database");
		// 3. generate JWT Token
		if(associateDetails != null) {
			String authToken = SecureLogin.createJWT(associateId, -1);
			LOGGER.debug("JWT Token generated for Associate - "+associateId);
			// construct response with Associate details and JWT token
			response.setAuthToken(authToken);
			response.setCode(200);
			response.setMessage("Login Success");
			response.setResponse(Arrays.asList(associateDetails));
		}
		
		return response;
	}
	
	void validateInput(String associateId) throws ScrumBoardException;
	
	Associate getAssociateDetails(String associateId);

}
