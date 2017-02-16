package com.fdu.rest;

import javax.ws.rs.Path;

//@Path("/services")
public class ComputingServicesService {

	/**
	 * Apply for a Job
	 * @param formData
	 * @param file
	 * @param fileDetail
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	/*@POST
	@Path("/applyJob")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response applyForAJob(@FormDataParam("model") String formData, @FormDataParam("file") InputStream file,
			@FormDataParam("file") FormDataContentDisposition fileDetail)
			throws JsonParseException, JsonMappingException, IOException {

		JobApplicant jobApplicantDetails = new ObjectMapper().readValue(formData, JobApplicant.class);
		byte[] fileData = IOUtils.toByteArray(file);
		jobApplicantDetails.setResume(fileData);
		Operations operation = new ComputingServicesOperations();
		boolean success = operation.saveJobApplicant(jobApplicantDetails);
		return success ? Response.ok(200).build() : Response.status(500).build();
	}

	*//**
	 * Admin - View all job applicants
	 * @return
	 *//*
	@GET
	@Path("/viewJobApplicants")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response viewJobApplicants() {
		Operations operation = Operations.getInstance();
		return Response.ok(operation.getAllJobApplicants()).build();
	}

	*//**
	 * Remove multiple job applicant from the race
	 * @param jobApplicants
	 * @return
	 *//*
	@POST
	@Path("/deleteJobApplicants")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteJobApplicants(List<JobApplicant> jobApplicants) {
		return Response.ok(200).build();
	}

	*//**
	 * Remove a job applicant from the race
	 * @param studentId
	 * @return
	 *//*
	@DELETE
	@Path("/deleteJobApplicant")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteJobApplicant(@QueryParam("studentId") Integer studentId) {
		return Response.ok(200).build();
	}

	*//**
	 * Update job applicant details
	 * @param formData
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 *//*
	@POST
	@Path("/updateJobApplicant")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response updateJobApplicant(@FormDataParam("model") String formData)
			throws JsonParseException, JsonMappingException, IOException {

		JobApplicant jobApplicantDetails = new ObjectMapper().readValue(formData, JobApplicant.class);
		return Response.ok(200).build();
	}

	*//**
	 * Hire a job applicant.<br/>
	 * This action hires a job applicant for the role of Lab Assistant.<br/>
	 * @param jobApplicantDetailsJSON
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 *//*
	@POST
	@Path("/hireJobApplicant")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response hireJobApplicant(String jobApplicantDetailsJSON)
			throws JsonParseException, JsonMappingException, IOException {

		JobApplicant jobApplicantDetails = new ObjectMapper().readValue(jobApplicantDetailsJSON, JobApplicant.class);
		ComputingServicesResponse response = Operations.getInstance().hireJobApplicant(jobApplicantDetails);
		return Response.ok(response).build();
	}
	
	*//**
	 * Email job applicant
	 * @param jobApplicantDetailsJSON
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 *//*
	@POST
	@Path("/emailJobApplicant")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response emailJobApplicant(String jobApplicantDetailsJSON)
			throws JsonParseException, JsonMappingException, IOException {

		JobApplicant jobApplicantDetails = new ObjectMapper().readValue(jobApplicantDetailsJSON, JobApplicant.class);
		return Response.ok(200).build();
	}*/

}
