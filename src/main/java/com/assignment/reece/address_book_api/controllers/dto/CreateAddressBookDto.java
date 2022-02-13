package com.assignment.reece.address_book_api.controllers.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAddressBookDto {
	@NotEmpty
	private String name;
}
