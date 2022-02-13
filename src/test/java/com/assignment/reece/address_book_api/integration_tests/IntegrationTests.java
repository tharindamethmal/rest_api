package com.assignment.reece.address_book_api.integration_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.assignment.reece.address_book_api.controllers.dto.ContactDto;
import com.assignment.reece.address_book_api.controllers.dto.CreateAddressBookDto;
import com.assignment.reece.address_book_api.controllers.dto.CreateContactDto;
import com.assignment.reece.address_book_api.controllers.dto.PhoneNumberDto;
import com.assignment.reece.address_book_api.util.PagableData;
import com.assignment.reece.address_book_api.util.PhoneNumberType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class IntegrationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Test
	public void testCreatingMultipleAddressBooks_MultipleContacts_And_GetAllUniqueContacts() throws IOException {

		// create first address book
		CreateAddressBookDto addressBook1 = new CreateAddressBookDto("address-book-1");
		ResponseEntity<String> responseEntity1 = this.restTemplate.postForEntity(
				Contstants.SERVER_URL + port + Contstants.APPLICATION_CONTEXT_PATH + "/address_books", addressBook1,
				String.class);
		assertEquals(HttpStatus.CREATED, responseEntity1.getStatusCode());
		int addressBook1Id = Integer.parseInt(responseEntity1.getBody());

		// create second address book
		CreateAddressBookDto addressBook2 = new CreateAddressBookDto("address-book-2");
		ResponseEntity<String> responseEntity2 = this.restTemplate.postForEntity(
				Contstants.SERVER_URL + port + Contstants.APPLICATION_CONTEXT_PATH + "/address_books", addressBook2,
				String.class);
		assertEquals(HttpStatus.CREATED, responseEntity2.getStatusCode());
		int addressBook2Id = Integer.parseInt(responseEntity2.getBody());

		// add contact to address book1
		PhoneNumberDto phoneNumber1 = new PhoneNumberDto(PhoneNumberType.MOBILE, "0772648723");
		PhoneNumberDto phoneNumber2 = new PhoneNumberDto(PhoneNumberType.RESIDENT, "0772648724");
		List<PhoneNumberDto> phoneNumbersList1 = new ArrayList<>();
		phoneNumbersList1.add(phoneNumber1);
		phoneNumbersList1.add(phoneNumber2);

		CreateContactDto contact1 = new CreateContactDto(addressBook1Id, "Jone", phoneNumbersList1);

		ResponseEntity<String> responseEntity3=this.restTemplate.postForEntity(
				Contstants.SERVER_URL + port + Contstants.APPLICATION_CONTEXT_PATH + "/contacts", contact1,
				String.class);
		assertEquals(HttpStatus.CREATED, responseEntity3.getStatusCode());

		// add contact to address book2
		PhoneNumberDto phoneNumber3 = new PhoneNumberDto(PhoneNumberType.MOBILE, "0772648723");
		PhoneNumberDto phoneNumber4 = new PhoneNumberDto(PhoneNumberType.WORK, "0772648725");
		List<PhoneNumberDto> phoneNumbersList2 = new ArrayList<>();
		phoneNumbersList2.add(phoneNumber3);
		phoneNumbersList2.add(phoneNumber4);

		CreateContactDto contact2 = new CreateContactDto(addressBook2Id, "Jone", phoneNumbersList2);

		ResponseEntity<String> responseEntity4=this.restTemplate.postForEntity(
				Contstants.SERVER_URL + port + Contstants.APPLICATION_CONTEXT_PATH + "/contacts", contact2,
				String.class);
		assertEquals(HttpStatus.CREATED, responseEntity4.getStatusCode());

		// get unique contacts
		ResponseEntity<PagableData> responseEntity5 = this.restTemplate.getForEntity(
				Contstants.SERVER_URL + port + Contstants.APPLICATION_CONTEXT_PATH + "/contacts?unique=true",
				PagableData.class);
		assertEquals(HttpStatus.OK, responseEntity5.getStatusCode());
		PagableData<ContactDto> pagableDataResponse1 = responseEntity5.getBody();
		assertEquals(1,pagableDataResponse1.getTotalRecords());
		
		

	}

}
