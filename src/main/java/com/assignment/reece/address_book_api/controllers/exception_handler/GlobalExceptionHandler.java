package com.assignment.reece.address_book_api.controllers.exception_handler;

import java.time.LocalDateTime;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.assignment.reece.address_book_api.exception.BadRequestException;
import com.assignment.reece.address_book_api.exception.NoDataException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ BadRequestException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public final ResponseEntity<Object> handleBadRequestExceptions(Exception ex) {

		ExceptionResponse response = new ExceptionResponse();
		response.setDateTime(LocalDateTime.now());
		response.setMessage(ex.getLocalizedMessage());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ NoDataException.class })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public final ResponseEntity<Object> handleNoContentException(Exception ex) {

		ExceptionResponse response = new ExceptionResponse();
		response.setDateTime(LocalDateTime.now());
		response.setMessage(ex.getLocalizedMessage());

		return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public final ResponseEntity<Object> handleConstraintViolationException(Exception ex) {

		ExceptionResponse response = new ExceptionResponse();
		response.setDateTime(LocalDateTime.now());
		response.setMessage(ex.getLocalizedMessage());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
