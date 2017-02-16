package com.fdu.impl;

import java.text.ParseException;
import java.util.List;

import com.arif.interfaces.ScrumBoard;
import com.arif.model.AddProjectModel;
import com.arif.model.Associate;
import com.arif.model.Project;
import com.arif.model.Scrum;
import com.fdu.response.ScrumBoardResponse;
import com.mongodb.MongoException;

public class ScrumBoardImpl implements ScrumBoard {

	@Override
	public Associate login(String associateId) {
		Associate associate = getLoginDBServiceInstance().getAssociateDetails(associateId);
		return associate;
	}

	@Override
	public boolean addProject(AddProjectModel projectDetails) {
		boolean isProjectAdded = false;
		// 1. Index

		// 2. Save to db
		try {
			isProjectAdded = getAddProjectDBServiceInstance().addProject(projectDetails.getProjectName());
		} catch (MongoException mongoException) {
			mongoException.printStackTrace();
		}

		return isProjectAdded;
	}

	@Override
	public List<Project> getAllProjects() {
		List<Project> projectList = getFetchAllProjectsDBServiceInstance().fetchAllProjects();
		return projectList;
	}

	@Override
	public ScrumBoardResponse<?> addAssociate(Associate associate) {
		return getAddAssociateInstance().addAssociate(associate);
	}

	@Override
	public ScrumBoardResponse<?> addScrum(Scrum scrum) {
		try {
			return getScrumOperationsInstance().addScrum(scrum);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ScrumBoardResponse<Scrum> getScrumDetails(String scrumDate, String projectName) {
		ScrumBoardResponse<Scrum> response = null;
		List<Scrum> scrumDetails;
		try {
			scrumDetails = getScrumOperationsInstance().getScrumDetails(scrumDate, projectName);
			response = new ScrumBoardResponse<>();
			response.setCode(200);
			if(scrumDetails.isEmpty()) {
				response.setMessage("No Scrum records for "+scrumDate);
			} else {
				response.setMessage("Showing Scrum details for "+scrumDate);
				response.setResponse(scrumDetails);
			}			
			return response;
		} catch (Exception exception) {
			response = new ScrumBoardResponse<>();
			response.setCode(500);
			response.setMessage("Internal Server Error");
		}
		return response;
	}

}
