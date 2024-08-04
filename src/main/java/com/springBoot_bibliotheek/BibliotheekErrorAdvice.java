package com.springBoot_bibliotheek;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import exceptions.BoekNotFoundException;
import exceptions.DuplicateBookException;

@RestControllerAdvice
public class BibliotheekErrorAdvice {
	@ResponseBody
	@ExceptionHandler(BoekNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String employeeNotFoundHandler(BoekNotFoundException ex) {
		return ex.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(DuplicateBookException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	String duplicateEmployeeHandler(DuplicateBookException ex) {
		return ex.getMessage();
	}
}
