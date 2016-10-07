package com.epam.example.ecinema.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epam.example.ecinema.domain.Client;
import com.epam.example.ecinema.service.ClientService;
import com.epam.example.ecinema.web.exception.ForbiddenException;

@Controller
@RequestMapping("/clients")
public class ClientController {
	@Autowired
	private ClientService service;
	
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	@ResponseBody
	public Client[] getAllClients() {
		List<Client> clients = service.clientAll();
		return clients.toArray(new Client[clients.size()]);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Client getClientById(@PathVariable("id") Long id) {
		Client client = service.getClientById(id);
		return client;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String deleteClient(@PathVariable("id") Long id, HttpSession session) {
		if (session.getAttribute("loginSession") == null) {
			throw new ForbiddenException();
		}
		service.removeClientById(id);
		return "";
	}
	
	@RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
	@ResponseBody
	public Long createClient(@RequestBody MultiValueMap<String, String> body, HttpServletResponse resp, HttpSession session) {
		if (session.getAttribute("loginSession") == null) {
			throw new ForbiddenException();
		}
		resp.setStatus(201);
		Client client = new Client();
		client.setId(0L);
		client.setFirstName(body.getFirst("firstName"));
		client.setLastName(body.getFirst("lastName"));
		Long id = service.createClient(client);
		return id;
	}
}
