package com.assignment.reece.address_book_api.controllers.exception_handler;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ExceptionResponse {
	private String message;
	private LocalDateTime dateTime;
}
