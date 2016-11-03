package com.epam.example.userrestservice;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class CreateUser extends ServerResource {
	@Post
	public Representation create(Representation data) {
		Status status = null;
		String message = null;
		
		Form form = new Form(data);
		String name = form.getFirstValue("name");
		String surname = form.getFirstValue("surname");
		String login = form.getFirstValue("login");
		String email = form.getFirstValue("email");
		
		if (name == null || surname == null || login == null || email == null) {
			message = "All fields are required!";
			status = Status.CLIENT_ERROR_BAD_REQUEST;
		} else {
			try {
				int id = Users.add(name, surname, login, email);
				message = "New user has been created and has an ID=" + id;
				status = Status.SUCCESS_CREATED;
			} catch (Exception e) {
				message = e.getMessage();
				status = Status.CLIENT_ERROR_BAD_REQUEST;
			}
		}
		setStatus(status);
		return new StringRepresentation(message, MediaType.TEXT_PLAIN);
	}
	
	public CreateUser() {
	}
	
}
