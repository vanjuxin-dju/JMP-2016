package com.epam.example.ecinema.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.example.ecinema.domain.Client;
import com.epam.example.ecinema.repository.ClientRepository;
import com.epam.example.ecinema.service.ClientService;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {
	@Autowired
	private ClientRepository clientRepository;

	@Override
	public List<Client> clientAll() {
		return clientRepository.findAll();
	}

	@Override
	public Client getClientById(Long id) {
		return clientRepository.findById(id);
	}

	@Override
	public void removeClientById(Long id) {
		clientRepository.deleteById(id);
	}

	@Override
	public Long createClient(Client client) {
		Long id = null;
		Client clientFromDb = clientRepository.findByName(client.getFirstName(), client.getLastName());
		if (clientFromDb == null) {
			id = clientRepository.create(client);
		} else {
			id = clientFromDb.getId();
		}
		return id;
	}

	@Override
	public void updateClient(Client client) {
		clientRepository.update(client);
		
	}

}
