package com.epam.example.ecinema.service.impl;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epam.example.ecinema.domain.Client;
import com.epam.example.ecinema.repository.ClientRepository;
import com.epam.example.ecinema.service.ClientService;

public class ClientServiceImplTest {
	@InjectMocks
	private ClientService service = new ClientServiceImpl();
	@Mock
	private ClientRepository repository;
	
	private static final String FIRST_NAME = "Firstname";
	private static final String LAST_NAME = "Lastname";
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void clientAllTest() {
		Mockito.when(repository.findAll()).thenReturn(new ArrayList<Client>());
		Assert.assertNotNull(service.clientAll());
	}
	
	@Test
	public void getClientByIdTest() {
		Mockito.when(repository.findById(2L)).thenReturn(new Client());
		Assert.assertNotNull(service.getClientById(2L));
	}
	
	@Test
	public void createClientTest() {
		Client client = new Client();
		client.setId(1L);
		client.setFirstName(FIRST_NAME);
		client.setLastName(LAST_NAME);
		
		Mockito.when(repository.findByName(FIRST_NAME, LAST_NAME)).thenReturn(null);
		Mockito.when(repository.create(client)).thenReturn(1L);
		Assert.assertEquals(Long.valueOf(1L), service.createClient(client));
	}
	
	@Test
	public void createClientExistingTest() {
		Client client = new Client();
		client.setId(1L);
		client.setFirstName(FIRST_NAME);
		client.setLastName(LAST_NAME);
		
		Mockito.when(repository.findByName(FIRST_NAME, LAST_NAME)).thenReturn(client);
		Assert.assertEquals(Long.valueOf(1L), service.createClient(client));
	}
}
