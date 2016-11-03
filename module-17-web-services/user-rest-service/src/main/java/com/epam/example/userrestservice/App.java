package com.epam.example.userrestservice;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class App extends Application {

	@Override
	public synchronized Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.attach("/read", GetAllUsers.class);
		router.attach("/read/{id}", GetUser.class);
		router.attach("/read/{id}/avatar", GetUserImage.class);
		router.attach("/create", CreateUser.class);
		router.attach("/update/{id}", UpdateUser.class);
		router.attach("/create/{id}/avatar", UploadUserImage.class);
		router.attach("/delete/{id}", DeleteUser.class);
		return router; 
	}
	
}
