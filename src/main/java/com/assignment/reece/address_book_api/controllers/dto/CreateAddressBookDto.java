package com.assignment.reece.address_book_api.controllers.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class CreateAddressBookDto {
	@NotEmpty
	private String name;
}
