package com.arif.interfaces;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.arif.exception.ScrumBoardException;
import com.arif.model.Associate;
import com.arif.response.ScrumBoardResponse;
import com.arif.util.SecureLogin;

/**
 * Handles services related to user login
 * 
 * @author arifakrammohammed
 *
 */
public interface LoginService {

	final static Logger LOGGER = Logger.getLogger(LoginService.class);

	/**
	 * Perform user login after input validation. If user is valid responds his
	 * request with a token.
	 * 
	 * @param associate
	 *            associate to login
	 * @return {@link ScrumBoardResponse} containing {@link Associate} details
	 * @throws NoSuchAlgorithmException
	 *             if the Secret key to validate a user against does not exist;
	 *             this happens when you restart the system, and user tries
	 *             logging in with old token
	 */
	default ScrumBoardResponse<Associate> login(Associate associate) throws NoSuchAlgorithmException {
		ScrumBoardResponse<Associate> response = new ScrumBoardResponse<>();
		// 1. validate the input
		try {
			LOGGER.debug("Validating input for user login");
			validateInput(associate.getAssociateId());
			LOGGER.debug("Input validated - OK. Proceeding with login");
		} catch (ScrumBoardException e) {
			LOGGER.error("User Input Not Valid : " + associate.getAssociateId(), e);
			// construct message with error details
			response.setCode(404);
			response.setMessage("Login ID cannot contain these characters [ <> \" ! \' : { } ]");
			return response;
		}
		// 2. check if associate present in the system (with given user id and password)
		Associate associateDetails = getAssociateDetails(associate);
		if (associateDetails != null) {
			// 3. check if password was reset
			if(associate.getAssociateId().equals(associate.getPassword())) {
				LOGGER.info("Associate needs to update password as it was reset "+associate.getAssociateId());
				response.setCode(404);
				response.setMessage("Your password was reset. Please update your password");
				return response;
			}
			LOGGER.debug("Associate login success "+associate.getAssociateId());
			String authToken = SecureLogin.createJWT(associate.getAssociateId(), -1);
			LOGGER.debug("JWT Token generated for Associate. User can login");
			// construct response with Associate details and JWT token
			response.setAuthToken(authToken);
			response.setCode(200);
			response.setMessage("Login Success");
			response.setResponse(Arrays.asList(associateDetails));
		} else {
			// if given login id is not present in the system
			LOGGER.info("Either the login or password is incorrect "+associate.getAssociateId());
			// construct message with error details
			response.setCode(403);
			response.setMessage("Either the Id is invalid or you have typed in the wrong Password. If you do not have to the system, please check with your supervisor");
		}
		return response;
	}

	/**
	 * check associates id if per rules
	 * 
	 * @param associateId
	 *            user id to validate
	 * @throws ScrumBoardException
	 *             if user input is not per set rules
	 */
	void validateInput(String associateId) throws ScrumBoardException;

	/**
	 * return the details of associate with given associate ID
	 * 
	 * @param associateId
	 *            associate whose details are to be retrieved
	 * @return {@link Associate} details
	 */
	Associate getAssociateDetails(Associate associate);

}
