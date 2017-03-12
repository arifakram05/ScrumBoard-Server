package com.arif.impl;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.Document;
import org.codehaus.jackson.map.ObjectMapper;

import com.arif.constants.Constants;
import com.arif.exception.ScrumBoardException;
import com.arif.interfaces.LoginService;
import com.arif.model.Associate;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * contains all the logic realted to user login
 * 
 * @author arifakrammohammed
 *
 */
public class LoginServiceImpl implements LoginService {

	MongoDatabase database;

	public LoginServiceImpl(MongoDatabase database) {
		super();
		this.database = database;
	}

	/**
	 * check if the given associate exists in our records
	 * 
	 * @param associateId
	 *            ID of the associate
	 * @return <b>true</b> if associate exists, otherwise <b>false</b>
	 */
	public boolean isAssociateExists(String associateId) {
		MongoCollection<Document> associatesCollection = database.getCollection("associates");
		long resultsCount = associatesCollection.count(eq("associateId", associateId));
		if (resultsCount != 0)
			return true;
		else
			return false;
	}

	/**
	 * fetches given associates's details
	 * 
	 * @param associate
	 *            associate details
	 * @return {@link Associate}
	 */
	public Associate getAssociateDetails(Associate loginDetails) {
		List<Associate> associateList = new ArrayList<>(1);
		// get collection
		MongoCollection<Document> associatesCollection = database.getCollection(Constants.ASSOCIATES.getValue());
		// process retrieved data
		Block<Document> processRetreivedData = (document) -> {

			String retrivedDataAsJSON = document.toJson();
			Associate associate;
			try {
				associate = new ObjectMapper().readValue(retrivedDataAsJSON, Associate.class);
				associateList.add(associate);
			} catch (IOException e) {
				LOGGER.error("Error occurred while processing fetched associates data while login", e);
			}
		};
		// query
		associatesCollection
				.find(and(eq(Constants.ASSOCIATEID.getValue(), loginDetails.getAssociateId().trim()),
						eq(Constants.PASSWORD.getValue(), loginDetails.getPassword())))
				.forEach(processRetreivedData);

		if (associateList.isEmpty()) {
			return null;
		}

		return associateList.get(0);
	}

	@Override
	public void validateInput(String associateId) throws ScrumBoardException {
		Pattern pattern = Pattern.compile(Constants.RESTRICT.getValue());
		Matcher matcher = pattern.matcher(associateId);
		while (matcher.find()) {
			throw new ScrumBoardException("Invalid input");
		}
	}

}
