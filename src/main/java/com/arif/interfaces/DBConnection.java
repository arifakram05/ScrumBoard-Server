package com.arif.interfaces;

import com.arif.database.DBConnecton;
import com.mongodb.client.MongoDatabase;

public interface DBConnection {

	default MongoDatabase getDBConnection() {
		return DBConnecton.getConnection();
	}

}
