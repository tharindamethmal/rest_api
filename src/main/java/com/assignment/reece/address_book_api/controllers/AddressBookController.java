package com.assignment.reece.address_book_api.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.reece.address_book_api.controllers.dto.ContactDto;
import com.assignment.reece.address_book_api.controllers.dto.CreateAddressBookDto;
import com.assignment.reece.address_book_api.persistence.entities.Contact;
import com.assignment.reece.address_book_api.service.AddressBooksService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("address_books")
public class AddressBookController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AddressBooksService addressBooksService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@Operation(description = "Create a new address book")
	public void createAddressBook(@Valid @RequestBody CreateAddressBookDto createAddressBookDto) {
		logger.info("Http request received to create a new address book with name {}", createAddressBookDto.getName());

		addressBooksService.createAddressBook(createAddressBookDto.getName());

		logger.info("Successfully created an address book with name {}", createAddressBookDto.getName());

	}

	@DeleteMapping(value = "/{addressBookId}")
	@Operation(description = "Delete an existing address book")
	public void deleteAddressBook(@PathVariable Integer addressBookId) {
		logger.info("Http request received to delete address book with id {}", addressBookId);

		addressBooksService.deleteAddressBook(addressBookId);

		logger.info("Successfully deleted the address book with id {}", Integer.toString(addressBookId));

	}

	@GetMapping(value = "/{addressBookId}")
	@Operation(description = "Get all the contacts in the address book")
	public List<ContactDto> getAllContactsInAddressBook(@PathVariable Integer addressBookId) {

		List<Contact> contacts = addressBooksService.getAllContactsInAddressBook(addressBookId);
		List<ContactDto> contactDtos = contacts.stream().map(ContactDto::convert).collect(Collectors.toList());

		return contactDtos;
	}

}
