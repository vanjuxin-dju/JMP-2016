package com.epam.example.ecinema.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epam.example.ecinema.domain.Client;
import com.epam.example.ecinema.domain.Reservation;
import com.epam.example.ecinema.service.ClientService;
import com.epam.example.ecinema.service.ReservationService;

@Controller
@RequestMapping("/reservations")
public class ReservationController {
	@Autowired
	private ReservationService service;
	@Autowired
	private ClientService clientService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public Reservation[] getAllReservations() {
		List<Reservation> reservations = service.reservationAll();
		return reservations.toArray(new Reservation[reservations.size()]);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Reservation getReservationById(@PathVariable("id") Long id) {
		Reservation reservation = service.getReservationById(id);
		return reservation;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String deleteReservation(@PathVariable("id") Long id, HttpServletResponse resp) {
		resp.setStatus(204);
		service.removeReservationById(id);
		return "";
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public Long createReservation(@RequestBody MultiValueMap<String, String> body, HttpServletResponse resp) {
		resp.setStatus(201);
		Long id = 0L;
		Reservation reservation = new Reservation();
		reservation.setId(0L);
		reservation.setFilmName(body.getFirst("filmName"));
		reservation.setDate(new Date(body.getFirst("date")));
		reservation.setPrice(Double.valueOf(body.getFirst("price")));
		reservation.setSeat(Integer.valueOf(body.getFirst("seat")));
		Client client = clientService.getClientById(Long.valueOf(body.getFirst("clientId")));
		if (client != null) {
			reservation.setClient(client);
			id = service.createReservation(reservation);
		}
		return id;
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public String updateReservation(@PathVariable("id") Long id, @RequestBody MultiValueMap<String, String> body, HttpServletResponse resp) {
		Reservation reservation = new Reservation();
		reservation.setId(id);
		reservation.setFilmName(body.getFirst("filmName"));
		reservation.setDate(new Date(body.getFirst("date")));
		reservation.setPrice(Double.valueOf(body.getFirst("price")));
		reservation.setSeat(Integer.valueOf(body.getFirst("seat")));
		Client client = clientService.getClientById(Long.valueOf(body.getFirst("clientId")));
		if (client != null) {
			reservation.setClient(client);
			service.updateReservation(reservation);
		}
		return "";
	}
}
