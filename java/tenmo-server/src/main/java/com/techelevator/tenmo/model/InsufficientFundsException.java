package com.techelevator.tenmo.model;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InsufficientFundsException extends Exception {
	private static final long serialVersionUID = 1L;

}
