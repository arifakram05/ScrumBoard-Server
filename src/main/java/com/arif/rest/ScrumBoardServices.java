package com.arif.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.arif.interfaces.ScrumBoard;
import com.arif.model.Associate;
import com.arif.model.Jira;
import com.arif.model.Project;
import com.arif.model.ProjectNotes;
import com.arif.model.Scrum;
import com.arif.model.ScrumDetails;
import com.arif.response.ScrumBoardResponse;
import com.sun.jersey.multipart.FormDataParam;

@Path("/services")
public class ScrumBoardServices {

	/**
	 * Register a new user
	 * 
	 * @param associateDetails
	 *            new user details
	 * @param isRegistration
	 *            indicates if a new associate is being added or an existing one
	 *            is being updated
	 * @return {@link ScrumBoardResponse} containing response details
	 */
	@POST
	@Path("/register")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public ScrumBoardResponse<Void> register(@FormDataParam("associateDetails") String associateDetails,
			@QueryParam("isRegistration") boolean isRegistration) {

		ScrumBoardResponse<Void> response = ScrumBoard.getInstance().register(associateDetails, isRegistration);
		return response;
	}

	/**
	 * Validate user credentials and log-in
	 * 
	 * @param associateDetails
	 *            associate to log-in
	 * @return {@link ScrumBoardResponse} containing {@link Associate} details
	 */
	@POST
	@Path("/login")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public ScrumBoardResponse<Associate> login(@FormDataParam("associateDetails") String associateDetails) {

		ScrumBoardResponse<Associate> response = ScrumBoard.getInstance().login(associateDetails);
		return response;
	}

	/**
	 * update user password
	 * 
	 * @param associateDetails
	 *            associate whose password is to be updated
	 * @return {@link ScrumBoardResponse} containing response details
	 */
	@POST
	@Path("/update/password/")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public ScrumBoardResponse<Void> updatePassword(@FormDataParam("associateDetails") String associateDetails) {

		ScrumBoardResponse<Void> response = ScrumBoard.getInstance().updatePassword(associateDetails);
		return response;
	}

	/**
	 * Add a new project
	 * 
	 * @param projectName
	 *            name of the new project to add
	 * @param associateId
	 *            user doing this operation
	 * @param token
	 *            JWT token
	 * @return {@link ScrumBoardResponse} containing response details
	 */
	@POST
	@Path("/project")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public ScrumBoardResponse<Void> saveProject(@FormDataParam("project") String project,
			@FormDataParam("associateId") String associateId, @HeaderParam("Authorization") String token) {

		ScrumBoardResponse<Void> response = ScrumBoard.getInstance().addProject(project, associateId, token);
		return response;
	}

	/**
	 * Returns all projects. Does not validate the token.
	 * 
	 * @return {@link ScrumBoardResponse} containing {@link Project}s
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
	 * @param associateDetails
	 *            {@link Associate} details
	 * @param associateId
	 *            user doing this operation
	 * @param token
	 *            JWT token
	 * @param isRegistration
	 *            indicates if a new associate is being added or an existing one
	 *            is being updated
	 * @return {@link ScrumBoardResponse} containing response details
	 */
	@POST
	@Path("/associate")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public ScrumBoardResponse<Void> saveAssociate(@FormDataParam("associateDetails") String associateDetails,
			@FormDataParam("associateId") String associateId, @HeaderParam("Authorization") String token,
			@QueryParam("isRegistration") boolean isRegistration) {

		ScrumBoardResponse<Void> response = ScrumBoard.getInstance().addAssociate(associateDetails, associateId, token,
				isRegistration);
		return response;
	}

	/**
	 * Add a Scrum for a project
	 * 
	 * @param scrumDetails
	 *            {@link ScrumDetails} to add
	 * @param associateId
	 *            user doing this operation
	 * @param token
	 *            JWT token
	 * @return {@link ScrumBoardResponse} containing response details
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
	 * @return {@link ScrumBoardResponse} containing {@link Scrum} details
	 */
	@POST
	@Path("/scrumdetails")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public ScrumBoardResponse<Scrum> getScrumDetails(@FormDataParam("scrumDate") String scrumDate,
			@FormDataParam("projectList") String projectList, @FormDataParam("associateId") String associateId,
			@HeaderParam("Authorization") String token) {

		ScrumBoardResponse<Scrum> response = ScrumBoard.getInstance().getScrumDetails(scrumDate, projectList,
				associateId, token);
		return response;
	}

	/**
	 * Get Scrum details for the given date.
	 * 
	 * @param scrumDate
	 *            date
	 * @param projectName
	 *            project name
	 * @param associateId
	 *            associate doing this operation
	 * @param token
	 *            JWT token
	 * @return {@link ScrumBoardResponse} containing {@link Scrum} details
	 */
	@POST
	@Path("/filteredscrumdetails")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public ScrumBoardResponse<Scrum> getFilteredScrumDetails(@FormDataParam("scrumDate") String scrumDate,
			@FormDataParam("projectName") String projectName, @FormDataParam("associateId") String associateId,
			@HeaderParam("Authorization") String token) {

		ScrumBoardResponse<Scrum> response = ScrumBoard.getInstance().getFilteredScrumDetails(scrumDate, projectName,
				associateId, token);
		return response;
	}

	/**
	 * Return recent scrum record for the given project.
	 * 
	 * @param projectName
	 *            project name
	 * @return {@link ScrumBoardResponse} containing {@link Scrum} details
	 */
	@GET
	@Path("/latestScrum")
	@Produces(MediaType.APPLICATION_JSON)
	public ScrumBoardResponse<Scrum> getRecentScrumRecord(@QueryParam("projectName") String projectName) {

		ScrumBoardResponse<Scrum> response = ScrumBoard.getInstance().getRecentScrumRecord(projectName);
		return response;
	}

	/**
	 * Update Scrum record of an associate for the given day.
	 * 
	 * @param scrumDetails
	 *            {@link ScrumDetails}
	 * @param date
	 *            date of the scrum
	 * @param projectName
	 *            project name
	 * @param associateId
	 *            associate doing this operation
	 * @param token
	 *            JWT token
	 * @return {@link ScrumBoardResponse} containing response details
	 */
	@POST
	@Path("/scrumupdate")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public ScrumBoardResponse<Void> dailyScrumUpdate(@FormDataParam("scrumDetails") String scrumDetails,
			@FormDataParam("date") String date, @FormDataParam("projectName") String projectName,
			@FormDataParam("associateId") String associateId, @HeaderParam("Authorization") String token) {

		ScrumBoardResponse<Void> response = ScrumBoard.getInstance().saveDailyScrumUpdate(scrumDetails, date,
				projectName, associateId, token);
		return response;
	}

	/**
	 * Returns all projects notes for the given project. Does not check the request if JWT token present
	 * 
	 * @param projectName
	 *            project name
	 * @return {@link ScrumBoardResponse} containing {@link ProjectNotes}
	 *         details
	 */
	@GET
	@Path("/projectNotes")
	@Produces(MediaType.APPLICATION_JSON)
	public ScrumBoardResponse<ProjectNotes> getAllProjectNotes(@QueryParam("projectName") String projectName) {

		ScrumBoardResponse<ProjectNotes> response = ScrumBoard.getInstance().getAllProjectNotes(projectName);
		return response;
	}

	/**
	 * Save a new project notes
	 * 
	 * @param projectNotes
	 *            JSON containing {@link ProjectNotes}
	 * @param projectName
	 *            name of the project
	 * @param associateId
	 *            user doing this operation
	 * @param token
	 *            JWT token
	 * @return {@link ScrumBoardResponse} containing response details
	 */
	@POST
	@Path("/projectNote")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public ScrumBoardResponse<Void> saveNewProjectNotes(@FormDataParam("projectNotes") String projectNotes,
			@FormDataParam("projectName") String projectName, @FormDataParam("associateId") String associateId,
			@HeaderParam("Authorization") String token) {

		ScrumBoardResponse<Void> response = ScrumBoard.getInstance().saveNewProjectNotes(projectNotes, projectName,
				associateId, token);
		return response;
	}

	/**
	 * delete a notes from a project
	 * 
	 * @param projectNotes
	 *            notes to delete
	 * @param projectName
	 *            name of the project a note belongs to
	 * @param associateId
	 *            user doing this operation
	 * @param token
	 *            JWT token
	 * @return {@link ScrumBoardResponse} containing response details
	 */
	@POST
	@Path("/delete/projectNote")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public ScrumBoardResponse<Void> deleteProjectNotes(@FormDataParam("projectNotes") String projectNotes,
			@FormDataParam("projectName") String projectName, @FormDataParam("associateId") String associateId,
			@HeaderParam("Authorization") String token) {
		ScrumBoardResponse<Void> response = ScrumBoard.getInstance().deleteProjectNotes(projectNotes, projectName,
				associateId, token);
		return response;
	}

	/**
	 * update a given notes from a given project
	 * 
	 * @param projectNotes
	 *            notes to edit
	 * @param projectName
	 *            name of the project given notes belongs to
	 * @param associateId
	 *            user doing this operation
	 * @param token
	 *            JWT token
	 * @return {@link ScrumBoardResponse} containing response details
	 */
	@POST
	@Path("/update/projectNote")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public ScrumBoardResponse<Void> editProjectNotes(@FormDataParam("projectNotes") String projectNotes,
			@FormDataParam("projectName") String projectName, @FormDataParam("associateId") String associateId,
			@HeaderParam("Authorization") String token) {
		ScrumBoardResponse<Void> response = ScrumBoard.getInstance().updateProjectNotes(projectNotes, projectName,
				associateId, token);
		return response;
	}

	/**
	 * Associate search by ID or Name
	 *
	 * @param searchText
	 *            Id or Name of the associate
	 * @return {@link ScrumBoardResponse} containing {@link Associate} details
	 */
	@GET
	@Path("/searchAssociate")
	@Produces(MediaType.APPLICATION_JSON)
	public ScrumBoardResponse<Associate> searchAssociate(@QueryParam("associate") String searchText) {
		ScrumBoardResponse<Associate> response = ScrumBoard.getInstance().searchAssociates(searchText);
		return response;
	}

	/**
	 * Add given associate to an existing scrum of a project
	 *
	 * @param scrumDetails
	 *            existing scrum details
	 * @param associateDetails
	 *            associate to add
	 * @param associateId
	 *            user doing this operation
	 * @param token
	 *            JWT token
	 * @return {@link ScrumBoardResponse} containing response details
	 */
	@POST
	@Path("/updateScrum")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public ScrumBoardResponse<Void> updateScrum(@FormDataParam("scrumDetails") String scrumDetails,
			@FormDataParam("associateDetails") String associateDetails,
			@FormDataParam("associateId") String associateId, @HeaderParam("Authorization") String token) {

		ScrumBoardResponse<Void> response = ScrumBoard.getInstance().updateScrum(scrumDetails, associateDetails,
				associateId, token);
		return response;
	}

	@GET
	@Path("/jira/{associateId}")
	@Produces(MediaType.APPLICATION_JSON)
	public ScrumBoardResponse<Jira> getJiraIssues(@PathParam("associateId") String associateId,
			@QueryParam("maxResults") int maxResults, @QueryParam("status") String status,
			@QueryParam("user") String userId, @HeaderParam("Authorization") String token) {
		ScrumBoardResponse<Jira> response = ScrumBoard.getInstance().getJiraIssues(associateId, maxResults, status, userId,
				token);
		return response;
	}

}
