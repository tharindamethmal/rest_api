package com.assignment.reece.address_book_api.service_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import com.assignment.reece.address_book_api.persistence.entities.AddressBook;
import com.assignment.reece.address_book_api.persistence.entities.Contact;
import com.assignment.reece.address_book_api.persistence.entities.PhoneNumber;
import com.assignment.reece.address_book_api.persistence.repositories.ContactRepository;
import com.assignment.reece.address_book_api.persistence.repositories.PhoneNumberRepository;
import com.assignment.reece.address_book_api.service.AddressBooksService;
import com.assignment.reece.address_book_api.service.ContactData;
import com.assignment.reece.address_book_api.service.ContactsService;
import com.assignment.reece.address_book_api.util.PagableData;
import com.assignment.reece.address_book_api.util.PhoneNumberType;

@ExtendWith(MockitoExtension.class)
public class ContactsServiceTest {

	@Mock
	private AddressBooksService addressBooksService;

	@Mock
	private ContactRepository contactRepository;

	@MockBean
	private PhoneNumberRepository phoneNumberRepository;

	@Autowired
	@InjectMocks
	private ContactsService contactsService;

	@Test
	public void testGetAllUniqueContacts() {
		AddressBook addrBook = new AddressBook();

		Contact contact1 = new Contact("Kevin", addrBook);
		PhoneNumber contact1Phone1 = new PhoneNumber(PhoneNumberType.MOBILE, "0782233445");
		PhoneNumber contact1Phone2 = new PhoneNumber(PhoneNumberType.WORK, "0782233446");
		List<PhoneNumber> contact1PhoneNumbers = new ArrayList<>();
		contact1PhoneNumbers.add(contact1Phone1);
		contact1PhoneNumbers.add(contact1Phone2);
		contact1.setPhoneNumbers(contact1PhoneNumbers);

		Contact contact2 = new Contact("John", addrBook);
		PhoneNumber contact2Phone1 = new PhoneNumber(PhoneNumberType.MOBILE, "0782233447");
		List<PhoneNumber> contact2PhoneNumbers = new ArrayList<>();
		contact2PhoneNumbers.add(contact2Phone1);
		contact2.setPhoneNumbers(contact2PhoneNumbers);

		Contact contact3 = new Contact("Kevin", addrBook);
		PhoneNumber contact3Phone1 = new PhoneNumber(PhoneNumberType.MOBILE, "0782233448");
		List<PhoneNumber> contact3PhoneNumbers = new ArrayList<>();
		contact3PhoneNumbers.add(contact3Phone1);
		contact3.setPhoneNumbers(contact3PhoneNumbers);

		List<String> uniqueNames = new ArrayList<>();
		uniqueNames.add(contact1.getName());
		uniqueNames.add(contact2.getName());
		Page<String> uniqueContactNamesPage = new PageImpl<>(uniqueNames);

		List<Contact> selectedContacts = new ArrayList<>();
		selectedContacts.add(contact1);
		selectedContacts.add(contact2);
		selectedContacts.add(contact3);

		PageRequest pageable = PageRequest.of(0, 5, Direction.ASC, "name");

		Mockito.when(contactRepository.findDistinctContactNames(pageable)).thenReturn(uniqueContactNamesPage);
		Mockito.when(contactRepository.findByNameIn(uniqueNames)).thenReturn(selectedContacts);

		PagableData<ContactData> actualResult = contactsService.getAllUniqueContacts(1, 5);
		assertEquals(actualResult.getData().get(0).getPhoneNumbers().size(), 3);

	}
}
