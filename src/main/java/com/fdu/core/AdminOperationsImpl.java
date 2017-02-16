package com.fdu.core;

public class AdminOperationsImpl {

	/*@Override
	public ComputingServicesResponse hireJobApplicant(JobApplicant jobApplicant) {
		String message;

		LabAssistant labAssistant = transferJobApplicantData(jobApplicant);
		// transfer candidate's details into labassistant collection from jobapplicants collection		
		boolean isSaveSuccess = DBOperations.getInstance().saveLabAssistant(labAssistant);
		if (isSaveSuccess) {
			// delete the candidate only when his/her details are persisted in labassistants collection
			boolean isDeleteSuccess = DBOperations.getInstance().deleteJobApplicant(jobApplicant.getStudentId());
			// authorize candidate for registration process
			
			if (isSaveSuccess && isDeleteSuccess) {
				message = "Job applicant is hired! You can now find his/her details with other Lab Assistants.";
				return GenericUtility.response(message, 1);
			} else {
				message = "Job applicant details could not deleted from Job applicants table. please delete it manually. However, Job applicant is hired and you can now find his/her details with other Lab Assistants.";
				return GenericUtility.response(message, 2);
			}
		} else {
			// if job applicant details could not be persisted, notify the user about the same
			message = "Something went wrong. Job applicant details could not be persisted.";
			return GenericUtility.response(message, 3);
		}
	}

	private LabAssistant transferJobApplicantData(JobApplicant jobApplicant) {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(jobApplicant, LabAssistant.class);
	}

	@Override
	public ComputingServicesResponse saveLabSchedule(LabSchedule labSchedule) {
		boolean isSuccess = DBOperations.getInstance().saveLabSchedule(labSchedule);
		if(isSuccess) {
			return GenericUtility.response("Lab Schedule saved!", 1);			
		} else {
			return GenericUtility.response("Something went wrong! Details could not be saved.", 3);
		}
	}*/
}
