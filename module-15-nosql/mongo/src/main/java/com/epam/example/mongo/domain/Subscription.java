package com.epam.example.mongo.domain;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("friendships")
public class Subscription {
	@Id
	private ObjectId id;

	private String idUser1;

	private String idUser2;

	private Date date;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getIdUser1() {
		return idUser1;
	}

	public void setIdUser1(String idUser1) {
		this.idUser1 = idUser1;
	}

	public String getIdUser2() {
		return idUser2;
	}

	public void setIdUser2(String idUser2) {
		this.idUser2 = idUser2;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
