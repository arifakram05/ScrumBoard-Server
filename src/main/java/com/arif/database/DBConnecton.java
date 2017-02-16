package com.arif.database;

import com.arif.util.ResourceReader;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DBConnecton {

	private static MongoClient mongoClient;
	private static MongoDatabase mongoDatabase;

	public DBConnecton() {

	}

	public static MongoClient establishConnection() {
		if (mongoClient == null || mongoDatabase == null) {
			// get database properties
			String dbLocation = ResourceReader.projectProperties.get("dbAddress");
			int dbPort = Integer.parseInt(ResourceReader.projectProperties.get("dbPort"));
			String dbName = ResourceReader.projectProperties.get("dbName");

			// establish connection
			mongoClient = new MongoClient(dbLocation, dbPort);
			// If database doesn't exists, MongoDB will create it
			mongoDatabase = mongoClient.getDatabase(dbName);
		}
		return mongoClient;
	}

	public static MongoDatabase getConnection() {
		return mongoDatabase;
	}

}
