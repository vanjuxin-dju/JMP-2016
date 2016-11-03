package com.epam.example.userrestservice;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.fileupload.RestletFileUpload;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class UploadUserImage extends ServerResource {
	@Post
	public Representation upload(Representation data) throws FileUploadException, IOException {
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
		if (data != null && MediaType.MULTIPART_FORM_DATA.equals(data.getMediaType(), true)) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(1000240);
            
            RestletFileUpload upload = new RestletFileUpload(factory);
            
            FileItemIterator fileIterator = upload.getItemIterator(data);
            
            boolean found = false;
            while (fileIterator.hasNext() && !found) {
                FileItemStream fi = fileIterator.next();
                if (fi.getFieldName().equals("avatar")) {
                    found = true;
                    
                    StringBuilder sb = new StringBuilder("media type: ");
                    sb.append(fi.getContentType()).append("\n");
                    sb.append("file name : ");
                    sb.append(fi.getName()).append("\n");
                    try {
	                    Image image = ImageIO.read(fi.openStream());
	                    Users.addAvatar(id, image);
                    } catch (IOException ex) {
                    	result = badRequest("Failed to convert an image. Make sure you've uploaded an image format.");
                    }
                    result = new StringRepresentation(sb.toString(), MediaType.TEXT_PLAIN);
                }
            }
		} else {
			result = badRequest("Failed to upload an image. Empty body or incorrect MediaType.");
		}
		return result;
	}
	
	private StringRepresentation badRequest(String msg) {
		Status error = new Status(Status.CLIENT_ERROR_BAD_REQUEST, msg);
		return new StringRepresentation(error.toString());
	}
	
	public UploadUserImage() {
	}

}
