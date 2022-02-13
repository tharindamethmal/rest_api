package com.assignment.reece.address_book_api.controllers.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateContactDto {
	@NotNull
	private Integer addressBookId;
	@NotEmpty
	private String contactPersonName;
	@NotNull
	@NotEmpty
	private List<PhoneNumberDto> phoneNumbers;
}
