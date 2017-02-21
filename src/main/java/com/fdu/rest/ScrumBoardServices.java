package com.fdu.rest;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.arif.interfaces.ScrumBoard;
import com.arif.model.Associate;
import com.arif.model.Project;
import com.arif.model.Scrum;
import com.fdu.response.ScrumBoardResponse;
import com.sun.jersey.multipart.FormDataParam;

@Path("/services")
public class ScrumBoardServices {

	final static Logger logger = Logger.getLogger(ScrumBoardServices.class);

	/**
	 * Validate user credentials and log-in
	 * 
	 * @param associateId
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ScrumBoardResponse<Associate> login(String associateId) {

		ScrumBoardResponse<Associate> response = ScrumBoard.getInstance().login(associateId);
		return response;
	}

	/**
	 * Add a new project.
	 * 
	 * @param formData
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@POST
	@Path("/project")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public ScrumBoardResponse<Void> saveProject(@FormDataParam("projectName") String projectName,
			@FormDataParam("associateId") String associateId, @HeaderParam("Authorization") String token) {

		ScrumBoardResponse<Void> response = ScrumBoard.getInstance().addProject(projectName, associateId, token);
		return response;
	}

	/**
	 * Returns all projects. Does not validate the token.
	 * 
	 * @return
	 */
	@GET
	@Path("/projects")
	@Produces(MediaType.APPLICATION_JSON)
	public ScrumBoardResponse<Project> getAllProjects() {

		ScrumBoardResponse<Project> response = ScrumBoard.getInstance().getAllProjects();
		return response;
	}

	/**
	 * Add/Update an associate
	 * 
	 * @param formData
	 *            Associate Details to save
	 * @return operation success or failure response
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@POST
	@Path("/associate")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public ScrumBoardResponse<Void> saveAssociate(@FormDataParam("associateDetails") String associateDetails,
			@FormDataParam("associateId") String associateId, @HeaderParam("Authorization") String token) {

		ScrumBoardResponse<Void> response = ScrumBoard.getInstance().addAssociate(associateDetails, associateId, token);
		return response;
	}

	/**
	 * Add a Scrum for a project
	 * 
	 * @param formData
	 *            Scrum Details to save
	 * @return operation success or failure response
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@POST
	@Path("/scrum")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public ScrumBoardResponse<Void> addScrum(@FormDataParam("scrumDetails") String scrumDetails,
			@FormDataParam("associateId") String associateId, @HeaderParam("Authorization") String token) {

		ScrumBoardResponse<Void> response = ScrumBoard.getInstance().addScrum(scrumDetails, associateId, token);
		return response;
	}

	/**
	 * Get Scrum details for the given date.
	 * 
	 * @return
	 */
	@POST
	@Path("/scrumdetails")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public ScrumBoardResponse<Scrum> getScrumDetails(@FormDataParam("scrumDate") String scrumDate,
			@FormDataParam("projectList") String projectList, @FormDataParam("associateId") String associateId,
			@HeaderParam("Authorization") String token) {

		ScrumBoardResponse<Scrum> response = ScrumBoard.getInstance().getScrumDetails(scrumDate, projectList, associateId, token);
		return response;
	}

	/**
	 * Update Scrum record of an associate for the given day.
	 * 
	 * @param formData
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@POST
	@Path("/scrumupdate")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public ScrumBoardResponse<Void> dailyScrumUpdate(@FormDataParam("scrumDetails") String scrumDetails,
			@FormDataParam("date") String date, @FormDataParam("projectName") String projectName,
			@FormDataParam("associateId") String associateId, @HeaderParam("Authorization") String token) {

		ScrumBoardResponse<Void> response = ScrumBoard.getInstance().saveDailyScrumUpdate(scrumDetails, date, projectName, associateId, token); 
		return response;
	}	

}
