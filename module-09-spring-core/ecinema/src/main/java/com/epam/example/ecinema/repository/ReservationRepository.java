package com.epam.example.ecinema.repository;

import java.util.Date;
import java.util.List;

import com.epam.example.ecinema.domain.Reservation;
import com.epam.example.ecinema.repository.base.BaseRepository;

public interface ReservationRepository extends BaseRepository<Reservation> {
	List<Reservation> findByDate(Date date);
}
