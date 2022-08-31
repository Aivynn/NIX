package com.config;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MONGODB {


        private static final String URL = "localhost";
        private static final int PORT = 27017;
        private static final String DATABASE = "homework";
        private static MongoClient mongoClient;
        private static MongoDatabase db;

        private MONGODB() {
        }

        public static MongoDatabase getMongoDatabase() {
            if (mongoClient == null) {
                mongoClient = new MongoClient(URL, PORT);
                db = mongoClient.getDatabase(DATABASE);
            }
            return db;
        }
    }
