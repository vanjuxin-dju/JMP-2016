package com.epam.example.ecinema.repository.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.epam.example.ecinema.domain.Client;
import com.epam.example.ecinema.repository.ClientRepository;
import com.epam.example.ecinema.repository.base.AbstractBaseRepository;

@Repository
@Transactional
public class ClientRepositoryImpl extends AbstractBaseRepository<Client> implements ClientRepository {

	@Override
	public Client findByName(String firstName, String lastName) {
		@SuppressWarnings("deprecation")
		Criteria cr = getSession()
				.createCriteria(Client.class, "clients")
				.add(Restrictions.eq("firstName", firstName))
				.add(Restrictions.eq("lastName", lastName));
		return (Client) cr.uniqueResult();
	}

}
