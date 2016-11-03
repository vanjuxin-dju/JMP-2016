package com.epam.example.userrestservice;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.ObjectRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class GetUserImage extends ServerResource {
	@Get
	public Representation download(Representation data) {
		Representation result = null;
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
		try {
			if (user.getAvatar() != null) {
				BufferedImage image = (BufferedImage)user.getAvatar();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(image, "png", baos);
				ObjectRepresentation<byte[]> img = new ObjectRepresentation<byte[]>(baos.toByteArray(), MediaType.IMAGE_PNG) {
			        @Override
			        public void write(OutputStream os) throws IOException {
			            super.write(os);
			            os.write(this.getObject());
			        }
			    };
				result = img;
			} else {
				Status error = new Status(Status.CLIENT_ERROR_NOT_FOUND, "User with ID=" + id + " doesn't have an avatar\n");
				result = new StringRepresentation(error.toString());
			}
		} catch(IOException ex) {
			result = new StringRepresentation("FAIL", MediaType.TEXT_PLAIN);
		}
		return result;
	}
	
	private StringRepresentation badRequest(String msg) {
		Status error = new Status(Status.CLIENT_ERROR_BAD_REQUEST, msg);
		return new StringRepresentation(error.toString());
	}
	

}
