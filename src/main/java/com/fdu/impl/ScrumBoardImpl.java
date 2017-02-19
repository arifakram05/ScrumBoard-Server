package com.fdu.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.arif.interfaces.LoginService;
import com.arif.interfaces.ScrumBoard;
import com.arif.model.Associate;
import com.arif.model.Project;
import com.arif.model.Scrum;
import com.arif.model.ScrumDetails;
import com.arif.util.SecureLogin;
import com.fdu.response.ScrumBoardResponse;

public class ScrumBoardImpl implements ScrumBoard {

	final static Logger LOGGER = Logger.getLogger(LoginService.class);

	private boolean validateToken(String token, String associateIdToVerify) {
		return SecureLogin.isTokenValid(token, associateIdToVerify);
	}

	@Override
	public ScrumBoardResponse<Associate> login(String associateId) {
		ScrumBoardResponse<Associate> response = null;
		try {
			response = getLoginServiceInstance().login(associateId);
		} catch (Exception e) {
			LOGGER.error("Error while logging in the user ", e);
			response.setCode(500);
			response.setMessage("Internal Server Error");
		}
		return response;
	}

	@Override
	public ScrumBoardResponse<?> addProject(String projectName, String associateId, String token) {
		ScrumBoardResponse<?> response = null;
		try {
			//validate token
			if (validateToken(token, associateId)) {
				//as token is valid, proceed with request
				LOGGER.info("Token is valid for associate "+associateId +". Proceeding ahead with processing request");
				response = getProjectServiceInstance().addProject(projectName, associateId, token);				
			} else {
				//as token is invalid, do not process the request
				LOGGER.info("Token is not valid for associate "+associateId+". Cannot proceed ahead with the request");
				response = new ScrumBoardResponse<>();
				response.setCode(403);
				response.setMessage("System cannot proceed with your operation for security reasons. Please re-login and perform the operation again");
			}
		} catch (Exception e) {
			LOGGER.error("Error while adding project ", e);
			response = new ScrumBoardResponse<>();			
			response.setCode(500);
			response.setMessage("Internal Server Error. Could not save new project");
		}

		return response;
	}

	@Override
	public ScrumBoardResponse<Project> getAllProjects() {
		ScrumBoardResponse<Project> response = new ScrumBoardResponse<>();
		List<Project> projectList = null;
		try {
			projectList = getProjectServiceInstance().getAllProjects();
			response.setCode(200);
			response.setMessage("List of all projects");
			response.setResponse(projectList);
		} catch (Exception e) {
			LOGGER.error("Error while fetching all projects ", e);
			response.setCode(500);
			response.setMessage("Internal Server Error. Could not get project list");
		}		
		return response;
	}

	@Override
	public ScrumBoardResponse<?> addAssociate(String associateDetails, String associateId, String token) {
		ScrumBoardResponse<?> response;
		Associate associate;
		try {			
			//validate token
			if (validateToken(token, associateId)) {
				//convert JSON to POJO
				associate = new ObjectMapper().readValue(associateDetails, Associate.class);
				//as token is valid, proceed with request
				response = getAssociateServiceInstance().addAssociate(associate);
				LOGGER.info("Token is valid for associate "+associateId +". Proceeding ahead with processing request");
			} else {
				//as token is invalid, do not process the request
				LOGGER.info("Token is not valid for associate "+associateId+". Cannot proceed ahead with the request");
				response = new ScrumBoardResponse<>();
				response.setCode(403);
				response.setMessage("System cannot proceed with your operation for security reasons. Please re-login and perform the operation again");
			}
		} catch (Exception e) {
			LOGGER.error("Error occurred while processing request ",e);
			response = new ScrumBoardResponse<>();
			response.setCode(500);
			response.setMessage("Internal Server Error. Could not add an associate to the system");
		}
		
		return response;
	}

	@Override
	public ScrumBoardResponse<?> addScrum(String scrumDetails, String associateId, String token) {
		ScrumBoardResponse<?> response;
		Scrum scrum;
		try {
			//convert JSON to POJO
			scrum = new ObjectMapper().readValue(scrumDetails, Scrum.class);
			//validate token
			if (validateToken(token, associateId)) {
				//as token is valid, proceed with request 
				response = getScrumServiceInstance().addScrum(scrum);
				LOGGER.info("Token is valid for associate "+associateId +". Proceeding ahead with processing request");
			} else {
				//as token is invalid, do not process the request
				LOGGER.info("Token is not valid for associate "+associateId+". Cannot proceed ahead with the request");
				response = new ScrumBoardResponse<>();
				response.setCode(403);
				response.setMessage("System cannot proceed with your operation for security reasons. Please re-login and perform the operation again");
			}
		} catch (Exception e) {
			LOGGER.error("Error occurred while processing request ",e);
			response = new ScrumBoardResponse<>();
			response.setCode(500);
			response.setMessage("Internal Server Error. Could not add a Scrum to the system");
		}		
		
		return response;
	}

	@Override
	public ScrumBoardResponse<Scrum> getScrumDetails(String scrumDate, String projectName, String associateId, String token) {
		ScrumBoardResponse<Scrum> response = null;
		List<Scrum> scrumDetails;
		try {
			//validate token
			if (validateToken(token, associateId)) {
				//as token is valid, proceed with request
				LOGGER.info("Token is valid for associate "+associateId +". Proceeding ahead with processing request");
				scrumDetails = getScrumServiceInstance().getScrumDetails(scrumDate, projectName);
				response = new ScrumBoardResponse<>();
				response.setCode(200);
				if(scrumDetails.isEmpty()) {
					response.setMessage("No Scrum records found for "+scrumDate);
				} else {
					response.setMessage("Showing Scrum details for "+scrumDate);
					response.setResponse(scrumDetails);
				}				
			} else {
				//as token is invalid, do not process the request
				LOGGER.info("Token is not valid for associate "+associateId+". Cannot proceed ahead with the request");
				response = new ScrumBoardResponse<>();
				response.setCode(403);
				response.setMessage("System cannot proceed with your operation for security reasons. Please re-login and perform the operation again");
			}
		} catch (Exception exception) {
			response = new ScrumBoardResponse<>();
			response.setCode(500);
			response.setMessage("Internal Server Error. Could not get Scrum details");
		}
		return response;
	}

	@Override
	public ScrumBoardResponse<?> saveDailyScrumUpdate(String scrumDetails, String date, String projectName, String associateId, String token) {
		ScrumBoardResponse<Scrum> response = null;
		try {
			//validate token
			if (validateToken(token, associateId)) {
				//as token is valid, proceed with request
				LOGGER.info("Token is valid for associate "+associateId +". Proceeding ahead with processing request");
				ScrumDetails scrumDetailsPojo = new ObjectMapper().readValue(scrumDetails, ScrumDetails.class);
				getScrumServiceInstance().saveDailyScrumUpdate(scrumDetailsPojo, date, projectName);
				response = new ScrumBoardResponse<>();
				response.setCode(200);
				response.setMessage("Saved");			
			} else {
				//as token is invalid, do not process the request
				LOGGER.info("Token is not valid for associate "+associateId+". Cannot proceed ahead with the request");
				response = new ScrumBoardResponse<>();
				response.setCode(403);
				response.setMessage("System cannot proceed with your operation for security reasons. Please re-login and perform the operation again");
			}			
		} catch (Exception exception) {
			response = new ScrumBoardResponse<>();
			response.setCode(500);
			response.setMessage("Internal Server Error. Could not save Scrum details");
		}
		return response;
	}

}
