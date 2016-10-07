package com.epam.example.ecinema.repository;

import com.epam.example.ecinema.domain.Client;
import com.epam.example.ecinema.repository.base.BaseRepository;

public interface ClientRepository extends BaseRepository<Client> {
	Client findByName(String firstName, String lastName);
}
