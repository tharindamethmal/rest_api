package com.assignment.reece.address_book_api.controllers.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.assignment.reece.address_book_api.persistence.entities.PhoneNumber;
import com.assignment.reece.address_book_api.util.PhoneNumberType;
import com.assignment.reece.address_book_api.util.ValidPhoneNumber;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumberDto {
	@NotNull
	private PhoneNumberType type;

	@NotEmpty
	@NotNull
	@ValidPhoneNumber
	private String number;

	public static PhoneNumber convert(PhoneNumberDto phoneNumberDto) {
		PhoneNumber PhoneNumberData = new PhoneNumber(phoneNumberDto.getType(), phoneNumberDto.getNumber());
		return PhoneNumberData;

	}

	public static PhoneNumberDto convert(PhoneNumber phoneNumber) {
		return new PhoneNumberDto(phoneNumber.getType(), phoneNumber.getNumber());

	}
}
