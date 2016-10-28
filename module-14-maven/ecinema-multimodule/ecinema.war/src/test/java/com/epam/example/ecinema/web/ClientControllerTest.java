package com.epam.example.ecinema.web;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epam.example.ecinema.domain.Client;
import com.epam.example.ecinema.service.ClientService;
import com.epam.example.ecinema.web.exception.ForbiddenException;

public class ClientControllerTest {
	@InjectMocks
	private ClientController controller = new ClientController();
	@Mock
	private ClientService service;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getAllClientsTest() {
		Mockito.when(service.clientAll()).thenReturn(new ArrayList<Client>());
		Assert.assertNotNull(controller.getAllClients());
	}
	
	@Test
	public void getClientByIdTest() {
		Client client = new Client();
		client.setId(2L);
		Mockito.when(service.getClientById(2L)).thenReturn(client);
		Assert.assertNotNull(controller.getClientById(2L));
	}
	
	@Test
	public void deleteClientTest() {
		HttpSession session = Mockito.mock(HttpSession.class);
		Mockito.when(session.getAttribute("loginSession")).thenReturn("adminadmin");
		Mockito.doNothing().when(service).removeClientById(3L);
		Assert.assertNotNull(controller.deleteClient(3L, session));
	}
	
	@Test(expected = ForbiddenException.class)
	public void deleteClientNotAuthorisedTest() {
		HttpSession session = Mockito.mock(HttpSession.class);
		Mockito.when(session.getAttribute("loginSession")).thenReturn(null);
		controller.deleteClient(3L, session);
	}

}
