package com.epam.example.mongo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;

import com.epam.example.mongo.domain.Message;
import com.epam.example.mongo.domain.Subscription;
import com.epam.example.mongo.domain.User;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class App {
	private static Date START_DATE = new Date(0);

	public static void main(String[] args) {
		final Morphia morphia = new Morphia();
		morphia.mapPackage("com.epam.example.mongo.domain");
		
		MongoCredential credential = MongoCredential.createCredential("social", "social", "s0C!aL".toCharArray());
		MongoClient client = new MongoClient(new ServerAddress("localhost"), Arrays.asList(credential));
		
		final Datastore datastore = morphia.createDatastore(client, "social");
		datastore.ensureIndexes();
		
		fillTheValues(datastore);

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
	
	private static void fillTheValues(Datastore datastore) {
		List<Object> userIds = new ArrayList<>();
		for (int i = 0; i < 1000000; i++) {
			// create users
			if (i != 0 && i % 3000 == 0) { // 3 thousand users were born in the same year
				START_DATE.setTime(START_DATE.getTime() + 86400000L);
			}
			
			User user = new User();
			user.setFirstName("AAA");
			user.setMiddleName("BBB");
			user.setLastName("CCC");
			user.setBirthday(START_DATE);
			Key<User> pk = datastore.save(user);
			System.out.println("Created user " + i);
			userIds.add(pk.getId());
			if (userIds.size() > 1) {
				// every new person have a previous user in friends...
				Subscription sb1 = new Subscription();
				sb1.setIdUser1(((ObjectId) userIds.get(userIds.size() - 1)).toString());
				sb1.setIdUser2(((ObjectId) userIds.get(userIds.size() - 2)).toString());
				sb1.setDate(new Date());
				datastore.save(sb1);
				System.out.println("He has sent a friendship request to previous user");
				
				// ...and vise versa
				Subscription sb2 = new Subscription();
				sb2.setIdUser2(((ObjectId) userIds.get(userIds.size() - 1)).toString());
				sb2.setIdUser1(((ObjectId) userIds.get(userIds.size() - 2)).toString());
				sb2.setDate(new Date());
				datastore.save(sb2);
				System.out.println("Previous user has accepted a friendship request");
				
				// every new person writes a message to previous user...
				Message msg1 = new Message();
				msg1.setFrom(((ObjectId) userIds.get(userIds.size() - 1)).toString());
				msg1.setTo(((ObjectId) userIds.get(userIds.size() - 2)).toString());
				msg1.setContent("Hello my new friend :)");
				msg1.setSentDate(new Date());
				datastore.save(msg1);
				
				// ...and he replies
				Message msg2 = new Message();
				msg2.setFrom(((ObjectId) userIds.get(userIds.size() - 2)).toString());
				msg2.setTo(((ObjectId) userIds.get(userIds.size() - 1)).toString());
				msg2.setContent("Hi my new friend :)");
				msg2.setSentDate(new Date());
				datastore.save(msg2);
				System.out.println("They have started a conversation");
			}
		}
		
		
	}

}
