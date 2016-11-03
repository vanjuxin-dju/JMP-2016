package com.epam.example.userrestservice;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.ServerResource;

public class DeleteUser extends ServerResource {
	@Delete
	public Representation delete() {
		String sid = (String) getRequest().getAttributes().get("id");
		if (sid == null)
			return badRequest("No ID provided\n"); 
		
		int id;
		try {
			id = Integer.parseInt(sid.trim());
		} catch (Exception e) {
			return badRequest("No such ID\n");
		}
		try {
			Users.delete(id);
		} catch (Exception e) {
			return badRequest(e.getMessage());
		}
		setStatus(Status.SUCCESS_NO_CONTENT);
		return new StringRepresentation("Deleted user with ID=" + id);
	}
	private StringRepresentation badRequest(String msg) {
		Status error = new Status(Status.CLIENT_ERROR_BAD_REQUEST, msg);
		return new StringRepresentation(error.toString());
	}
	public DeleteUser() {
	}
}
