package com.assignment.reece.address_book_api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.assignment.reece.address_book_api.persistence.entities.PhoneNumber;
import com.assignment.reece.address_book_api.service.ContactData;
import com.assignment.reece.address_book_api.service.ContactsService;
import com.assignment.reece.address_book_api.util.PagableData;
import com.assignment.reece.address_book_api.util.PhoneNumberType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(ContactsController.class)
@AutoConfigureMockMvc
public class ContactsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ContactsService contactsService;

	@Test
	public void getAllUniqueContactsShouldReturnASuccessfulResult() throws Exception {

		PagableData<ContactData> data = getPagableContactData();

		Mockito.when(contactsService.getAllUniqueContacts(1, 5)).thenReturn(data);

		RequestBuilder request = MockMvcRequestBuilders.get("/contacts?unique=true").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		this.mockMvc.perform(request).andExpect(status().isOk())
				.andExpect(jsonPath("$.data", hasSize(data.getData().size())))
				.andExpect(jsonPath("$.totalRecords", is((int) data.getTotalRecords())))
				.andExpect(jsonPath("$.data[0].contactPersonName", is(data.getData().get(0).getName())));
	}

	private PagableData<ContactData> getPagableContactData() {
		List<ContactData> contactDataList = new ArrayList<ContactData>();

		List<PhoneNumber> phoneNumbers1 = new ArrayList<>();
		PhoneNumber p1 = new PhoneNumber(PhoneNumberType.MOBILE, "0711122334");
		PhoneNumber p2 = new PhoneNumber(PhoneNumberType.RESIDENT, "0711122335");
		phoneNumbers1.add(p1);
		phoneNumbers1.add(p2);
		ContactData d1 = new ContactData("Jack", phoneNumbers1);
		contactDataList.add(d1);

		List<PhoneNumber> phoneNumbers2 = new ArrayList<>();
		PhoneNumber p3 = new PhoneNumber(PhoneNumberType.MOBILE, "0711122336");
		PhoneNumber p4 = new PhoneNumber(PhoneNumberType.RESIDENT, "0711122337");
		phoneNumbers1.add(p3);
		phoneNumbers1.add(p4);
		ContactData d2 = new ContactData("Steve", phoneNumbers2);
		contactDataList.add(d2);

		PagableData<ContactData> data = new PagableData<ContactData>(contactDataList, contactDataList.size(), 1, 5);
		return data;
	}

}
