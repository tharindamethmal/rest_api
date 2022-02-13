package com.assignment.reece.address_book_api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.reece.address_book_api.exception.BadRequestException;
import com.assignment.reece.address_book_api.exception.NoDataException;
import com.assignment.reece.address_book_api.persistence.entities.AddressBook;
import com.assignment.reece.address_book_api.persistence.entities.Contact;
import com.assignment.reece.address_book_api.persistence.repositories.AddressBookRepository;
import com.assignment.reece.address_book_api.persistence.repositories.ContactRepository;

@Service
public class AddressBooksService {

	@Autowired
	private AddressBookRepository addressBookRepository;

	@Autowired
	private ContactRepository contactRepository;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Transactional
	public void createAddressBook(String name) {
		AddressBook toBeSavedadddressBook = new AddressBook(name, new ArrayList<Contact>());
		assertValidAddressBook(toBeSavedadddressBook);

		addressBookRepository.save(toBeSavedadddressBook);
	}

	@Transactional
	public void deleteAddressBook(int addressBookId) {
		AddressBook addressBook = getAddressBook(addressBookId);
		addressBookRepository.delete(addressBook);
	}

	public AddressBook getAddressBook(Integer addressBookId) {
		Optional<AddressBook> optAddressBook = addressBookRepository.findById(addressBookId);
		if (optAddressBook.isEmpty()) {
			String errorMsg = "No address book found with id : " + addressBookId;
			logger.warn(errorMsg);
			throw new NoDataException(errorMsg);
		}

		return optAddressBook.get();
	}

	private void assertValidAddressBook(AddressBook addressBook) {
		if (addressBook.getName() == null || addressBook.getName().isEmpty()) {
			throw new BadRequestException("Address Book Name cannot be empty.");
		}

		if (addressBookRepository.existsByName(addressBook.getName())) {
			logger.warn("Duplicate Address Book name : {}", addressBook.getName());
			throw new BadRequestException("Duplicate Address Book name.");

		}

	}

	public List<Contact> getAllContactsInAddressBook(Integer addressBookId) {

		if (!addressBookRepository.existsById(addressBookId)) {
			throw new BadRequestException("No addressbook found for id : " + addressBookId);
		}
		List<Contact> contacts = contactRepository.findAllByAddressBook_Id(addressBookId);
		if (contacts == null || contacts.isEmpty()) {
			throw new NoDataException("No contacts found in address book " + addressBookId);
		}

		return contacts;

	}

}
