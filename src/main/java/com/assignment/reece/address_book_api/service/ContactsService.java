package com.assignment.reece.address_book_api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.reece.address_book_api.exception.BadRequestException;
import com.assignment.reece.address_book_api.exception.NoDataException;
import com.assignment.reece.address_book_api.persistence.entities.AddressBook;
import com.assignment.reece.address_book_api.persistence.entities.Contact;
import com.assignment.reece.address_book_api.persistence.entities.PhoneNumber;
import com.assignment.reece.address_book_api.persistence.repositories.ContactRepository;
import com.assignment.reece.address_book_api.persistence.repositories.PhoneNumberRepository;
import com.assignment.reece.address_book_api.util.PagableData;

@Service
public class ContactsService {
	@Autowired
	private AddressBooksService addressBooksService;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private PhoneNumberRepository phoneNumberRepository;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Transactional
	public void addNewContactToAddressBook(int addressBookId, String contactPersonName,
			List<PhoneNumber> phoneNumberDataList) {
		AddressBook addressBook = null;
		try {
			addressBook = addressBooksService.getAddressBook(addressBookId);
		} catch (NoDataException ex) {
			throw new BadRequestException("Invalid address book id : " + addressBookId);
		}

		Contact contact = new Contact(contactPersonName, addressBook);
		contact = contactRepository.save(contact);

		if (phoneNumberDataList != null && !phoneNumberDataList.isEmpty()) {
			for (PhoneNumber phoneNumber : phoneNumberDataList) {
				phoneNumber.setContact(contact);
			}
		}

		phoneNumberRepository.saveAll(phoneNumberDataList);

	}

	public Contact getContact(Integer contactId) {
		Optional<Contact> contactOpt = contactRepository.findById(contactId);
		if (contactOpt.isEmpty()) {
			String errorMsg = "No contact found with id : " + contactId;
			logger.warn(errorMsg);
			throw new NoDataException(errorMsg);
		}
		return contactOpt.get();

	}

	@Transactional
	public void deleteContact(Integer contactId) {
		Contact contact = getContact(contactId);
		contactRepository.delete(contact);

	}

	public PagableData<ContactData> getAllUniqueContacts(int page, int size) {
		// Page minimum value should be 0
		page = Math.max((Math.abs(page) - 1), 0);
		// Size should be positive NON ZERO integer
		size = Math.max(Math.abs(size), 1);

		PageRequest pageable = PageRequest.of(page, size, Direction.ASC, "name");
		Page<String> uniqueContactNamesPage = contactRepository.findDistinctContactNames(pageable);
		if (uniqueContactNamesPage.getNumberOfElements() == 0) {
			throw new NoDataException("No more contacts");
		}

		List<String> uniqueContactNames = uniqueContactNamesPage.getContent();
		List<Contact> selectedContacts = contactRepository.findByNameIn(uniqueContactNames);

		Map<String, List<Contact>> groupedByName = selectedContacts.stream()
				.collect(Collectors.groupingBy(Contact::getName));

		List<ContactData> contactDataList = new ArrayList<ContactData>();
		for (String contactName : groupedByName.keySet()) {
			List<PhoneNumber> phoneNumbers = new ArrayList<>();
			List<Contact> allEntriesWithGivenContactName = groupedByName.get(contactName);

			for (Contact contact : allEntriesWithGivenContactName) {
				if (contact.getPhoneNumbers() != null && !contact.getPhoneNumbers().isEmpty()) {
					phoneNumbers.addAll(contact.getPhoneNumbers());
				}
			}
			List<PhoneNumber> uniquePhoneNumbers = phoneNumbers.stream().distinct().collect(Collectors.toList());
			ContactData contactData = new ContactData(contactName, uniquePhoneNumbers);
			contactDataList.add(contactData);
		}

		return new PagableData<ContactData>(contactDataList, uniqueContactNamesPage.getTotalElements(), page, size);

	}
}
