package com.epam.example.mongo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class App {

	public static void main(String[] args) {
		MongoCredential credential = MongoCredential.createCredential("social", "social", "s0C!aL".toCharArray());
		MongoClient client = new MongoClient(new ServerAddress("localhost"), Arrays.asList(credential));
		MongoDatabase db = client.getDatabase("social");
		
		// minimum count of users by year of birth 
		MongoCollection<Document> social = db.getCollection("users");
		List<Bson> pl = new ArrayList<>();
		BasicDBObject byDate0 = new BasicDBObject();
		BasicDBObject group0 = new BasicDBObject("_id", new BasicDBObject("year", new BasicDBObject("$year", "$birthday")));
		group0.put("count", new BasicDBObject("$sum", 1));
		byDate0.put("$group", group0);
		pl.add(byDate0);
		
		BasicDBObject avg0 = new BasicDBObject();
		BasicDBObject grp0 = new BasicDBObject("_id", null);
		grp0.put("min", new BasicDBObject("$min", "$count"));
		avg0.put("$group", grp0);
		pl.add(avg0);
		
		AggregateIterable<Document> uss = social.aggregate(pl);
		System.out.println("\nMinimum count of users by year of birth:");
		uss.forEach((Block<? super Document>) System.out::println);
		
		// maximum count of subscriptions from month to month 
		social = db.getCollection("friendships");
		List<Bson> pipeline = new ArrayList<>();
		BasicDBObject byDate = new BasicDBObject();
		BasicDBObject id = new BasicDBObject("month", new BasicDBObject("$month", "$date"));
		id.put("year", new BasicDBObject("$year", "$date"));
		BasicDBObject group = new BasicDBObject("_id", id);
		group.put("count", new BasicDBObject("$sum", 1));
		byDate.put("$group", group);
		pipeline.add(byDate);
		
		BasicDBObject avg = new BasicDBObject();
		BasicDBObject grp = new BasicDBObject("_id", null);
		grp.put("max", new BasicDBObject("$max", "$count"));
		avg.put("$group", grp);
		pipeline.add(avg);
		AggregateIterable<Document> fss = social.aggregate(pipeline);
		System.out.println("\nMaximum count of subscriptions from month to month:");
		fss.forEach((Block<? super Document>) System.out::println);
		
		// average count of messages every day
		social = db.getCollection("messages");
		List<Bson> mPipeline = new ArrayList<>();
		BasicDBObject byDate1 = new BasicDBObject();
		BasicDBObject id1 = new BasicDBObject("month", new BasicDBObject("$month", "$sentDate"));
		id1.put("day", new BasicDBObject("$dayOfMonth", "$sentDate"));
		id1.put("year", new BasicDBObject("$year", "$sentDate"));
		BasicDBObject group1 = new BasicDBObject("_id", id1);
		group1.put("count", new BasicDBObject("$sum", 1));
		byDate1.put("$group", group1);
		mPipeline.add(byDate1);
		
		BasicDBObject avg1 = new BasicDBObject();
		BasicDBObject grp1 = new BasicDBObject("_id", null);
		grp1.put("avg", new BasicDBObject("$avg", "$count"));
		avg1.put("$group", grp1);
		mPipeline.add(avg1);
		AggregateIterable<Document> mss = social.aggregate(mPipeline);
		System.out.println("\nAverage count of messages every day:");
		mss.forEach((Block<? super Document>) System.out::println);
		
		client.close();
	}

}
