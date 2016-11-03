package com.epam.example.userrestservice;

import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GetUser extends ServerResource {
	@Get
	public Representation toXml() {
		String sid = (String) getRequest().getAttributes().get("id");
		if (sid == null)
			return badRequest("No ID provided\n"); 
		
		int id;
		try {
			id = Integer.parseInt(sid.trim());
		} catch (Exception e) {
			return badRequest("No such ID\n");
		} 
		
		User user = Users.get(id);
		if (user == null) {
			return badRequest("No user with ID=" + id + "\n"); 
		}
		DomRepresentation dom = null;
		try {
			dom = new DomRepresentation(MediaType.TEXT_XML);
			dom.setIndenting(true);
			Document doc = dom.getDocument();
			Element root = doc.createElement("user");
			
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(user.getName()));
			root.appendChild(name);
			
			Element surname = doc.createElement("surname");
			surname.appendChild(doc.createTextNode(user.getSurname()));
			root.appendChild(surname);
			
			Element login = doc.createElement("login");
			login.appendChild(doc.createTextNode(user.getLogin()));
			root.appendChild(login);
			
			Element email = doc.createElement("email");
			email.appendChild(doc.createTextNode(user.getEmail()));
			root.appendChild(email);
			
			doc.appendChild(root);
		} catch (Exception e) {
		}
		return dom;
	}
	
	private StringRepresentation badRequest(String msg) {
		Status error = new Status(Status.CLIENT_ERROR_BAD_REQUEST, msg);
		return new StringRepresentation(error.toString());
	}
	
	public GetUser() {
	}
}
