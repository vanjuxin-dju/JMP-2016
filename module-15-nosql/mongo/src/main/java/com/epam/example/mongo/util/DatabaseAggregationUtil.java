package com.epam.example.mongo.util;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DatabaseAggregationUtil {
	public static AggregateIterable<Document> getMinimumCountOfUsersByYearOfBirth(MongoDatabase db) {
		MongoCollection<Document> users = db.getCollection("users");
		List<Bson> pipeline = new ArrayList<>();
		BasicDBObject byDate = new BasicDBObject();
		BasicDBObject group = new BasicDBObject("_id", new BasicDBObject("year", new BasicDBObject("$year", "$birthday")));
		group.put("count", new BasicDBObject("$sum", 1));
		byDate.put("$group", group);
		pipeline.add(byDate);
		
		BasicDBObject min = new BasicDBObject();
		BasicDBObject minCount = new BasicDBObject("_id", null);
		minCount.put("min", new BasicDBObject("$min", "$count"));
		min.put("$group", minCount);
		pipeline.add(min);
		
		return users.aggregate(pipeline);
	}
	
	public static AggregateIterable<Document> getMaximumSubscriptionsFromMonthToMonth(MongoDatabase db) {
		MongoCollection<Document> friendships = db.getCollection("friendships");
		List<Bson> pipeline = new ArrayList<>();
		BasicDBObject byDate = new BasicDBObject();
		BasicDBObject id = new BasicDBObject("month", new BasicDBObject("$month", "$date"));
		id.put("year", new BasicDBObject("$year", "$date"));
		BasicDBObject group = new BasicDBObject("_id", id);
		group.put("count", new BasicDBObject("$sum", 1));
		byDate.put("$group", group);
		pipeline.add(byDate);
		
		BasicDBObject max = new BasicDBObject();
		BasicDBObject maxCount = new BasicDBObject("_id", null);
		maxCount.put("max", new BasicDBObject("$max", "$count"));
		max.put("$group", maxCount);
		pipeline.add(max);
		
		return friendships.aggregate(pipeline);
	}
	
	public static AggregateIterable<Document> getAverageCountOfMessagesEveryDay(MongoDatabase db) {
		MongoCollection<Document> messages = db.getCollection("messages");
		List<Bson> pipeline = new ArrayList<>();
		BasicDBObject byDate = new BasicDBObject();
		BasicDBObject id = new BasicDBObject("month", new BasicDBObject("$month", "$sentDate"));
		id.put("day", new BasicDBObject("$dayOfMonth", "$sentDate"));
		id.put("year", new BasicDBObject("$year", "$sentDate"));
		BasicDBObject group = new BasicDBObject("_id", id);
		group.put("count", new BasicDBObject("$sum", 1));
		byDate.put("$group", group);
		pipeline.add(byDate);
		
		BasicDBObject avg = new BasicDBObject();
		BasicDBObject grp1 = new BasicDBObject("_id", null);
		grp1.put("avg", new BasicDBObject("$avg", "$count"));
		avg.put("$group", grp1);
		pipeline.add(avg);
		
		return messages.aggregate(pipeline);
	}
	
	private DatabaseAggregationUtil() {
		// private constructor
	}

}
