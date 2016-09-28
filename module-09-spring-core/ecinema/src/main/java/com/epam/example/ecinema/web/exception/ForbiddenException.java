package com.epam.example.ecinema.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "You misspelled the date!")
public class ForbiddenException extends RuntimeException {
	public ForbiddenException() {
		super("You are not allowed to do this!");
	}
}
