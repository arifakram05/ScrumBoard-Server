package com.arif.interfaces;

import java.util.List;

import com.arif.db.AddProjectDBService;
import com.arif.db.FetchAllProjectsDBService;
import com.arif.db.LoginDBService;
import com.arif.model.AddProjectModel;
import com.arif.model.Associate;
import com.arif.model.Project;
import com.arif.model.Scrum;
import com.fdu.impl.AddAssociateImpl;
import com.fdu.impl.ScrumBoardImpl;
import com.fdu.impl.ScrumOperationsImpl;
import com.fdu.response.ScrumBoardResponse;

public interface ScrumBoard extends DBConnection {

	/**
	 * login a user to use system services
	 * 
	 * @param associateId
	 * @return
	 */
	Associate login(String associateId);

	/**
	 * add a new project
	 * 
	 * @param projectDetails
	 * @return
	 */
	boolean addProject(AddProjectModel projectDetails);

	/**
	 * get all projects
	 * 
	 * @return
	 */
	List<Project> getAllProjects();

	/**
	 * Add an associate to the system
	 * 
	 * @param associate
	 *            {@link Associate} details
	 * @return
	 */
	ScrumBoardResponse<?> addAssociate(Associate associate);

	/**
	 * Add Scrum to the system
	 * 
	 * @param associate
	 *            {@link Scrum} detail
	 * @return
	 */
	ScrumBoardResponse<?> addScrum(Scrum associate);

	/**
	 * Get Scrum details for a given date and project
	 * 
	 * @param scrumDate
	 *            Scrum date
	 * @param projectName
	 *            Project name
	 * @return
	 */
	ScrumBoardResponse<Scrum> getScrumDetails(String scrumDate, String projectName);

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

	default LoginDBService getLoginDBServiceInstance() {
		return new LoginDBService(getDBConnection());
	}

	default AddProjectDBService getAddProjectDBServiceInstance() {
		return new AddProjectDBService(getDBConnection());
	}

	default FetchAllProjectsDBService getFetchAllProjectsDBServiceInstance() {
		return new FetchAllProjectsDBService(getDBConnection());
	}

	default AddAssociate getAddAssociateInstance() {
		return new AddAssociateImpl(getDBConnection());
	}

	default ScrumOperations getScrumOperationsInstance() {
		return new ScrumOperationsImpl(getDBConnection());
	}

}
