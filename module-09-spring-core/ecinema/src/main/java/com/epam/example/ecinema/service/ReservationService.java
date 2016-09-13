package com.epam.example.ecinema.service;

import java.util.Date;
import java.util.List;

import com.epam.example.ecinema.domain.Reservation;

public interface ReservationService {
	Long createReservation(Reservation reservation);
	
	void updateReservation(Reservation reservation);

	Reservation getReservationById(Long id);

	List<Reservation> findReservationsByDate(Date date);

	List<Reservation> reservationAll();

	void removeReservationById(Long id);
}
