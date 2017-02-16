package com.arif.db;

import static com.mongodb.client.model.Filters.eq;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.codehaus.jackson.map.ObjectMapper;

import com.arif.model.Associate;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class LoginDBService {

	MongoDatabase database;

	public LoginDBService(MongoDatabase database) {
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
	 * @param associateId
	 *            Id of an associate
	 * @return {@link Associate}
	 */
	public Associate getAssociateDetails(String associateId) {
		List<Associate> associateList = new ArrayList<>(1);

		MongoCollection<Document> associatesCollection = database.getCollection("associates");
		Block<Document> processRetreivedData = (document) -> {

			String retrivedDataAsJSON = document.toJson();
			Associate associate;
			try {
				associate = new ObjectMapper().readValue(retrivedDataAsJSON, Associate.class);
				associateList.add(associate);
			} catch (IOException e) {
				e.printStackTrace();
			}

		};
		associatesCollection.find(eq("associateId", associateId)).forEach(processRetreivedData);
		
		if(associateList.isEmpty()) {
			return null;
		}

		return associateList.get(0);
	}
}
