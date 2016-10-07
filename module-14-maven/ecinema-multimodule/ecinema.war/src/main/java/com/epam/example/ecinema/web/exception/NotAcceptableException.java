package com.epam.example.ecinema.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "You misspelled the date!")
public class NotAcceptableException extends RuntimeException {
	public NotAcceptableException() {
		super("You misspelled the date!");
	}
}
