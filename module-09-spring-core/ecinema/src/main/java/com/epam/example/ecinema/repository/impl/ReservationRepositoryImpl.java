package com.epam.example.ecinema.repository.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.epam.example.ecinema.domain.Reservation;
import com.epam.example.ecinema.repository.ReservationRepository;
import com.epam.example.ecinema.repository.base.AbstractBaseRepository;

@Repository
@Transactional
public class ReservationRepositoryImpl extends AbstractBaseRepository<Reservation> implements ReservationRepository {

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<Reservation> findByDate(Date date) {
		Criteria cr = getSession()
				.createCriteria(Reservation.class, "reservations")
				.add(Restrictions.eq("res_date", date));
		return cr.list();
	}
	
}
