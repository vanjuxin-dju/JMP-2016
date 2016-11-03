package com.epam.example.userrestservice;

import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

public class UpdateUser extends ServerResource {
	@Put
	public Representation update(Representation data) {
		String sid = (String) getRequest().getAttributes().get("id");
		if (sid == null)
			return badRequest("No ID provided\n"); 
		
		int id;
		try {
			id = Integer.parseInt(sid.trim());
		} catch (Exception e) {
			return badRequest("No such ID\n");
		} 
		
		Form form = new Form(data);
		String name = form.getFirstValue("name");
		String surname = form.getFirstValue("surname");
		String login = form.getFirstValue("login");
		String email = form.getFirstValue("email");
		if (name == null || surname == null || login == null || email == null) {
			return badRequest("All fields are required!");
		}
		
		try {
			Users.put(id, name, surname, login, email);
		} catch (Exception e) {
			return badRequest(e.getMessage());
		}
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("name", name);
		obj.put("surname", surname);
		obj.put("login", login);
		obj.put("email", email);
		return new JsonRepresentation(obj);
	}
	
	private StringRepresentation badRequest(String msg) {
		Status error = new Status(Status.CLIENT_ERROR_BAD_REQUEST, msg);
		return new StringRepresentation(error.toString());
	}
	
	public UpdateUser() {
	}
	
}
