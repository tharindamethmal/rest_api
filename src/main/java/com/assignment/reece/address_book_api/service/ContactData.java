package com.assignment.reece.address_book_api.service;

import java.util.List;

import com.assignment.reece.address_book_api.persistence.entities.PhoneNumber;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactData {
	private String name;
	private List<PhoneNumber> phoneNumbers;
}
