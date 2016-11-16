package com.epam.example.mongo;

import java.util.Arrays;

import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.epam.example.mongo.util.DatabaseAggregationUtil;
import com.epam.example.mongo.util.DatabaseFillUtil;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoDatabase;

public class App {
	

	public static void main(String[] args) {
		final Morphia morphia = new Morphia();
		morphia.mapPackage("com.epam.example.mongo.domain");
		
		MongoCredential credential = MongoCredential.createCredential("social", "social", "s0C!aL".toCharArray());
		MongoClient client = new MongoClient(new ServerAddress("localhost"), Arrays.asList(credential));
		
		final Datastore datastore = morphia.createDatastore(client, "social");
		datastore.ensureIndexes();
		
		DatabaseFillUtil.fillTheDatabase(datastore);

		MongoDatabase db = client.getDatabase("social");
		
		AggregateIterable<Document> usersByYearOfBirth = DatabaseAggregationUtil.getMinimumCountOfUsersByYearOfBirth(db);
		System.out.println("\nMinimum count of users by year of birth:");
		usersByYearOfBirth.forEach((Block<? super Document>) System.out::println);
		
		AggregateIterable<Document> subscriptionsFromMonthToMonth = DatabaseAggregationUtil.getMaximumSubscriptionsFromMonthToMonth(db);
		System.out.println("\nMaximum count of subscriptions from month to month:");
		subscriptionsFromMonthToMonth.forEach((Block<? super Document>) System.out::println);
		
		AggregateIterable<Document> averageCountOfMessagesEveryDay = DatabaseAggregationUtil.getAverageCountOfMessagesEveryDay(db);
		System.out.println("\nAverage count of messages every day:");
		averageCountOfMessagesEveryDay.forEach((Block<? super Document>) System.out::println);
		
		client.close();
	}
	
	

}
