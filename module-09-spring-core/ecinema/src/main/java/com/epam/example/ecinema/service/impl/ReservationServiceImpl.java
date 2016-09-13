package com.epam.example.ecinema.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.example.ecinema.domain.Reservation;
import com.epam.example.ecinema.repository.ReservationRepository;
import com.epam.example.ecinema.service.ReservationService;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {
	@Autowired
	private ReservationRepository reservationRepository;

	@Override
	public Reservation getReservationById(Long id) {
		return reservationRepository.findById(id);
	}

	@Override
	public List<Reservation> findReservationsByDate(Date date) {
		return reservationRepository.findByDate(date);
	}

	@Override
	public List<Reservation> reservationAll() {
		return reservationRepository.findAll();
	}

	@Override
	public void removeReservationById(Long id) {
		reservationRepository.deleteById(id);
	}

	@Override
	public Long createReservation(Reservation reservation) {
		Long id = null;
		Reservation reservationFromDatabase = null;
		if (reservation.getId() != null && reservation.getId() != 0) {
			reservationFromDatabase = reservationRepository.findById(reservation.getId());
		}
		if (reservationFromDatabase != null) {
			reservationRepository.update(reservation);
		} else {
			id = reservationRepository.create(reservation);
		}
		return id;
	}

	@Override
	public void updateReservation(Reservation reservation) {
		reservationRepository.update(reservation);
	}

}
