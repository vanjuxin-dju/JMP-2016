package com.epam.example.userrestservice;

import java.util.List;

import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GetAllUsers extends ServerResource {
	@Get
	public Representation toXml() {
		List<User> users = Users.getAll();
		DomRepresentation dom = null;
		try {
			dom = new DomRepresentation(MediaType.TEXT_XML);
			dom.setIndenting(true);
			Document doc = dom.getDocument();
			
			Element root = doc.createElement("users");
			for (User user: users) {
				Element el = doc.createElement("user");
				el.setAttribute("id", user.getId() + "");
				el.setAttribute("name", user.getName());
				el.setAttribute("surname", user.getSurname());
				el.setAttribute("login", user.getLogin());
				el.setAttribute("email", user.getEmail());
				root.appendChild(el);
			}
			doc.appendChild(root);
		} catch (Exception e) {
		}
		return dom;
	}
	
	public GetAllUsers() {
	}
}
