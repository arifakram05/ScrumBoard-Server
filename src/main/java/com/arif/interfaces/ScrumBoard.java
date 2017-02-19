package com.arif.interfaces;

import com.arif.db.LoginServiceImpl;
import com.arif.db.ProjectServiceImpl;
import com.arif.model.Associate;
import com.arif.model.Project;
import com.arif.model.Scrum;
import com.fdu.impl.AssociateServiceImpl;
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
	ScrumBoardResponse<?> addProject(String projectName, String associateId, String token);

	/**
	 * get all projects
	 * 
	 * @return
	 */
	ScrumBoardResponse<Project> getAllProjects();

	/**
	 * Add an associate to the system
	 * 
	 * @param associate
	 *            {@link Associate} details
	 * @return
	 */
	ScrumBoardResponse<?> addAssociate(String associateDetails, String associateId, String token);

	/**
	 * Add Scrum to the system
	 * 
	 * @param associate
	 *            {@link Scrum} detail
	 * @return
	 */
	ScrumBoardResponse<?> addScrum(String scrumDetails, String associateId, String token);

	/**
	 * Get Scrum details for a given date and project
	 * 
	 * @param scrumDate
	 *            Scrum date
	 * @param projectName
	 *            Project name
	 * @return
	 */
	ScrumBoardResponse<Scrum> getScrumDetails(String scrumDate, String projectList, String associateId, String token);

	/**
	 * Save daily Scrum updates of an associate
	 * 
	 * @param scrumDetail
	 * @param date
	 * @param projectName
	 * @return
	 */
	ScrumBoardResponse<?> saveDailyScrumUpdate(String scrumDetails, String date, String projectName, String associateId, String token);

	/**
	 * Java 8 feature.<br/>
	 * Get the object of the implementations class
	 * 
	 * @return {@link ScrumBoardImpl} Object of the class that implements this
	 *         interface
	 */
	static ScrumBoardImpl getInstance() {
		return new ScrumBoardImpl();
	}

	/*
	 * below methods gives instances of service classes i.e. these are all the
	 * services this system offers
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

}
