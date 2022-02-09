package com.assignment.reece.address_book_api.controllers.dto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.assignment.reece.address_book_api.persistence.entities.Contact;
import com.assignment.reece.address_book_api.service.ContactData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {
	private String contactPersonName;
	private List<PhoneNumberDto> phoneNumbers;
	private OffsetDateTime createdAt;

	public static ContactDto convert(Contact contact) {
		List<PhoneNumberDto> phoneNumbers = null;
		if (contact.getPhoneNumbers() != null) {
			phoneNumbers = contact.getPhoneNumbers().stream().map(PhoneNumberDto::convert).collect(Collectors.toList());
		}
		return new ContactDto(contact.getName(), phoneNumbers, contact.getCreatedAt());
	}

	public static ContactDto convert(ContactData data) {
		List<PhoneNumberDto> phoneNumbers = null;
		if (data.getPhoneNumbers() != null) {
			phoneNumbers = data.getPhoneNumbers().stream().map(PhoneNumberDto::convert).collect(Collectors.toList());
		}
		return new ContactDto(data.getName(), phoneNumbers, null);
	}
}
