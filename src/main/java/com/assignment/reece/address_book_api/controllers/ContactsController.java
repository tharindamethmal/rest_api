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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.reece.address_book_api.controllers.dto.ContactDto;
import com.assignment.reece.address_book_api.controllers.dto.CreateContactDto;
import com.assignment.reece.address_book_api.controllers.dto.PhoneNumberDto;
import com.assignment.reece.address_book_api.exception.MethodNotImplementedException;
import com.assignment.reece.address_book_api.persistence.entities.PhoneNumber;
import com.assignment.reece.address_book_api.service.ContactData;
import com.assignment.reece.address_book_api.service.ContactsService;
import com.assignment.reece.address_book_api.util.PagableData;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("contacts")
public class ContactsController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ContactsService contactsService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@Operation(description = "Add a new contact to an existing address book")
	public void addContact(@Valid @RequestBody CreateContactDto createContactDto) {
		logger.info("Http request received to add a new contact to address book with id {}",
				createContactDto.getAddressBookId());
		List<PhoneNumber> phoneNumberDataList = createContactDto.getPhoneNumbers().stream().map(PhoneNumberDto::convert)
				.collect(Collectors.toList());
		contactsService.addNewContactToAddressBook(createContactDto.getAddressBookId(),
				createContactDto.getContactPersonName(), phoneNumberDataList);

		logger.info("Successfully added a new contact with name {} to address book with id ",
				createContactDto.getContactPersonName(), Integer.toString(createContactDto.getAddressBookId()));
	}

	@DeleteMapping(value = "/{contactId}")
	@Operation(description = "Delete an existing contact")
	public void deleteContact(@PathVariable Integer contactId) {
		logger.info("Http request received to delete contact with id {}", contactId);
		contactsService.deleteContact(contactId);
		logger.info("Successfully deleted the contact with id {}", Integer.toString(contactId));

	}

	@GetMapping
	@Operation(description = "Get all contacts ordered in ascending contact name ")
	public PagableData<ContactDto> getAllContacts(
			@RequestParam(value = "unique", defaultValue = "true") boolean isUniqueContactsOnly,
			@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size) {
		if (isUniqueContactsOnly) {
			PagableData<ContactData> contactsPage = contactsService.getAllUniqueContacts(page, size);
			List<ContactDto> contactDtoList = contactsPage.getData().stream().map(ContactDto::convert)
					.collect(Collectors.toList());
			return new PagableData<ContactDto>(contactDtoList, contactsPage.getTotalRecords(), contactsPage.getPage(),
					contactsPage.getSize());
		} else {
			throw new MethodNotImplementedException("Not supported to get contacts with duplicates");
		}
	}
}
