package com.fdu.rest;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.arif.interfaces.ScrumBoard;
import com.arif.model.AddProjectModel;
import com.arif.model.Associate;
import com.arif.model.Project;
import com.arif.model.Scrum;
import com.arif.model.ScrumDetails;
import com.arif.util.SecureLogin;
import com.fdu.response.ScrumBoardResponse;
import com.sun.jersey.multipart.FormDataParam;

@Path("/services")
public class ScrumBoardServices {

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
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(String associateId) throws JsonParseException, JsonMappingException, IOException {

		Associate associateDetails = null;
		ScrumBoardResponse<Associate> response = new ScrumBoardResponse<>();
		try {
			associateDetails = ScrumBoard.getInstance().login(associateId);
			if (associateDetails != null) {
				// as the system recognizes the user, an auth token will be
				// provided
				String authToken = SecureLogin.createJWT(associateId, -1);
				// construct response with Associate details and auth token
				response.setAuthToken(authToken);
				response.setCode(200);
				response.setMessage("Login Success");
				response.setResponse(Arrays.asList(associateDetails));
			} else {
				// construct response to notify the user that he is not a
				// recognized user
				response.setCode(403);
				response.setMessage(
						"You do not have access to log-in to this system. Please check with Project lead or Scrum master.");
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			response.setAuthToken(null);
			response.setCode(500);
			response.setMessage("Failed to genereate auth token");
		} catch (Exception e) {
			e.printStackTrace();
			response.setAuthToken(null);
			response.setCode(500);
			response.setMessage("Internal Server Error");
		}

		return Response.ok(response).build();
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
	public Response saveProject(@FormDataParam("projectDetails") String formData)
			throws JsonParseException, JsonMappingException, IOException {

		AddProjectModel projectModelDetails = new ObjectMapper().readValue(formData, AddProjectModel.class);
		// validate request
		if (!SecureLogin.isTokenValid(projectModelDetails.getAuthToken(), projectModelDetails.getAssociateId())) {
			// construct response for forbidden access
			ScrumBoardResponse<?> response = new ScrumBoardResponse<>();
			response.setCode(403);
			response.setMessage("Auth Token Not Recognized");
			return Response.ok(response).build();
		}
		// save new project in the system
		boolean isProjectAdded = ScrumBoard.getInstance().addProject(projectModelDetails);
		if (isProjectAdded) {
			// construct response for forbidden access
			ScrumBoardResponse<?> response = new ScrumBoardResponse<>();
			response.setCode(200);
			response.setMessage("Project Add operation success");
			return Response.ok(response).build();
		} else {
			return Response.ok(500).build();
		}
	}

	/**
	 * Returns all projects. Does not validate a user.
	 * 
	 * @return
	 */
	@GET
	@Path("/projects")
	@Produces(MediaType.APPLICATION_JSON)
	public Response viewJobApplicants() {
		List<Project> projectList = ScrumBoard.getInstance().getAllProjects();

		// create response
		ScrumBoardResponse<Project> response = new ScrumBoardResponse<>();
		response.setCode(200);
		response.setMessage("List of all projects");
		response.setResponse(projectList);

		return Response.ok(response).build();
	}

	/**
	 * Add an associate to the system
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
	public ScrumBoardResponse<?> saveAssociate(@FormDataParam("associateDetails") String formData)
			throws JsonParseException, JsonMappingException, IOException {

		Associate associateDetails = new ObjectMapper().readValue(formData, Associate.class);
		ScrumBoardResponse<?> response;
		// validate request

		// save new associate in the system
		response = ScrumBoard.getInstance().addAssociate(associateDetails);
		return response;
	}

	/**
	 * Add a Scrum to the system
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
	public ScrumBoardResponse<?> addScrum(@FormDataParam("scrumDetails") String formData)
			throws JsonParseException, JsonMappingException, IOException {

		Scrum scrumDetails = new ObjectMapper().readValue(formData, Scrum.class);
		ScrumBoardResponse<?> response;
		// validate request

		// save new Scrum in the system
		response = ScrumBoard.getInstance().addScrum(scrumDetails);
		return response;
	}

	/**
	 * Return Scrum details for the given date.
	 * 
	 * @return
	 */
	@GET
	@Path("/scrumdetails")
	@Produces(MediaType.APPLICATION_JSON)
	// TODO Accept an array of project names
	public ScrumBoardResponse<Scrum> getScrumDetails(@QueryParam("scrumDate") String scrumDate,
			@QueryParam("projectName") String projectName) {
		return ScrumBoard.getInstance().getScrumDetails(scrumDate, projectName);
	}

	/**
	 * Update targets for the day.
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
	public ScrumBoardResponse<?> dailyScrumUpdate(@FormDataParam("scrumDetails") String formData,
			@FormDataParam("date") String date, @FormDataParam("projectName") String projectName)
			throws JsonParseException, JsonMappingException, IOException {

		ScrumDetails scrumDetails = new ObjectMapper().readValue(formData, ScrumDetails.class);

		return ScrumBoard.getInstance().saveDailyScrumUpdate(scrumDetails, date, projectName);
	}

}
