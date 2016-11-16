package com.epam.example.mongo.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;

import com.epam.example.mongo.domain.Message;
import com.epam.example.mongo.domain.Subscription;
import com.epam.example.mongo.domain.User;

public class DatabaseFillUtil {
	private static Date START_DATE = new Date(0);
	
	public static void fillTheDatabase(Datastore datastore) {
		List<Object> userIds = new ArrayList<>();
		for (int i = 0; i < 1000000; i++) {
			// create users
			if (i != 0 && i % 3000 == 0) { // 3 thousand users have the same birthday
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
	
	private DatabaseFillUtil() {
		// private constructor
	}
}
