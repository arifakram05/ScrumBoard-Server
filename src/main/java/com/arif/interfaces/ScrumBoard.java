package com.arif.interfaces;

import com.arif.db.LoginServiceImpl;
import com.arif.db.ProjectServiceImpl;
import com.arif.model.Associate;
import com.arif.model.Project;
import com.arif.model.ProjectNotes;
import com.arif.model.Scrum;
import com.fdu.impl.AssociateServiceImpl;
import com.fdu.impl.ProjectNotesServiceImpl;
import com.fdu.impl.ScrumBoardImpl;
import com.fdu.impl.ScrumServiceImpl;
import com.fdu.response.ScrumBoardResponse;

public interface ScrumBoard extends DBConnection {

	/**
	 * login a user to use system services
	 * 
	 * @param associateId
	 * @return
	 */
	ScrumBoardResponse<Associate> login(String associateId);

	/**
	 * add a new project
	 * 
	 * @param projectDetails
	 * @return
	 */
	ScrumBoardResponse<Void> addProject(String projectName, String associateId, String token);

	/**
	 * get all projects
	 * 
	 * @return
	 */
	ScrumBoardResponse<Project> getAllProjects();

	/**
	 * Add/Update an associate
	 * 
	 * @param associate
	 *            {@link Associate} details
	 * @return
	 */
	ScrumBoardResponse<Void> addAssociate(String associateDetails, String associateId, String token);

	/**
	 * Add Scrum for a project
	 * 
	 * @param associate
	 *            {@link Scrum} detail
	 * @return
	 */
	ScrumBoardResponse<Void> addScrum(String scrumDetails, String associateId, String token);

	/**
	 * Get Scrum details for a given date and projects
	 * 
	 * @param scrumDate
	 * @param projectList
	 * @param associateId
	 * @param token
	 * @return
	 */
	ScrumBoardResponse<Scrum> getScrumDetails(String scrumDate, String projectList, String associateId, String token);

	/**
	 * Get filtered Scrum details for a given date and a given project
	 * 
	 * @param scrumDate
	 * @param projectName
	 * @param associateId
	 * @param token
	 * @return
	 */
	ScrumBoardResponse<Scrum> getFilteredScrumDetails(String scrumDate, String projectName, String associateId, String token);
	

	/**
	 * Save daily Scrum updates of an associate
	 * 
	 * @param scrumDetail
	 * @param date
	 * @param projectName
	 * @return
	 */
	ScrumBoardResponse<Void> saveDailyScrumUpdate(String scrumDetails, String date, String projectName, String associateId, String token);

	/**
	 * get all notes of a project
	 * 
	 * @return
	 */
	ScrumBoardResponse<ProjectNotes> getAllProjectNotes(String projectName);
	
	/**
	 * Save a new project note
	 * 
	 * @param projectNotes
	 * @param projectName
	 * @param associateId
	 * @param token
	 * @return
	 */
	ScrumBoardResponse<Void> saveNewProjectNotes(String projectNotes, String projectName, String associateId, String token);

	/**
	 * Java 8 feature.<br/>
	 * Get an object of the implementations class
	 * 
	 * @return {@link ScrumBoardImpl} Object of the class that implements this
	 *         interface
	 */
	static ScrumBoardImpl getInstance() {
		return new ScrumBoardImpl();
	}

	/*
	 * below methods provide instances of implementation classes
	 */

	default LoginService getLoginServiceInstance() {
		return new LoginServiceImpl(getDBConnection());
	}

	default ProjectService getProjectServiceInstance() {
		return new ProjectServiceImpl(getDBConnection());
	}

	default AssociateService getAssociateServiceInstance() {
		return new AssociateServiceImpl(getDBConnection());
	}

	default ScrumService getScrumServiceInstance() {
		return new ScrumServiceImpl(getDBConnection());
	}

	default ProjectNotesService getProjectNotesServiceInstance() {
		return new ProjectNotesServiceImpl(getDBConnection());
	}

}
