package com.epam.example.ecinema.service;

import java.util.List;

import com.epam.example.ecinema.domain.Client;

public interface ClientService {
	Long createClient(Client client);
	
	void updateClient(Client client);

	List<Client> clientAll();

	Client getClientById(Long id);

	void removeClientById(Long id);
}
