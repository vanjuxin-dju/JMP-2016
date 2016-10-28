package com.epam.example.ecinema.service.impl;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epam.example.ecinema.domain.Reservation;
import com.epam.example.ecinema.repository.ReservationRepository;
import com.epam.example.ecinema.service.ReservationService;

public class ReservationServiceImplTest {
	@InjectMocks
	private ReservationService service = new ReservationServiceImpl();
	@Mock
	private ReservationRepository repository;
	
	private static final Long DATE = 156487524575742L;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void reservationAllTest() {
		Mockito.when(repository.findAll()).thenReturn(new ArrayList<Reservation>());
		Assert.assertNotNull(service.reservationAll());
	}
	
	@Test
	public void reservationsByDateTest() {
		Mockito.when(repository.findByDate(new Date(DATE))).thenReturn(new ArrayList<Reservation>());
		Assert.assertNotNull(service.findReservationsByDate(new Date(DATE)));
	}
	
	@Test
	public void reservationByIdTest() {
		Mockito.when(repository.findById(2L)).thenReturn(new Reservation());
		Assert.assertNotNull(service.getReservationById(2L));
	}
	
	@Test
	public void createReservationTest() {
		Reservation reservation = new Reservation();
		reservation.setId(0L);
		Mockito.when(repository.create(reservation)).thenReturn(56L);
		Assert.assertEquals(Long.valueOf(56L), service.createReservation(reservation));
	}
	
	@Test
	public void createReservationExistingTest() {
		Reservation reservation = new Reservation();
		reservation.setId(56L);
		Mockito.when(repository.findById(reservation.getId())).thenReturn(reservation);
		Mockito.when(repository.update(reservation)).thenReturn(reservation);
		Assert.assertNull(service.createReservation(reservation));
	}

}
