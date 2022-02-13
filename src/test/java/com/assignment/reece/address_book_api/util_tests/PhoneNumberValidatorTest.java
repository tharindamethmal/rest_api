package com.assignment.reece.address_book_api.util_tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.assignment.reece.address_book_api.util.PhoneNumberValidator;

public class PhoneNumberValidatorTest {

	@Test
	public void checkValidPhoneNumbers() {
		PhoneNumberValidator PhoneNumberValidator = new PhoneNumberValidator();
		List<String> testPhoneNumbers = new ArrayList<String>();
		testPhoneNumbers.add("0772648723");
		testPhoneNumbers.add("+614121112224");
		testPhoneNumbers.add("202 555 0125");
		testPhoneNumbers.add("(202) 555-0125");
		testPhoneNumbers.add("+111 (202) 555-0125");
		
		for(String phoneNumber: testPhoneNumbers) {
			boolean actualResult = PhoneNumberValidator.isValid(phoneNumber, null);
			assert(actualResult);
		}
		
	}
	
	@Test
	public void checkInValidPhoneNumbers() {
		PhoneNumberValidator PhoneNumberValidator = new PhoneNumberValidator();
		List<String> testPhoneNumbers = new ArrayList<String>();
		testPhoneNumbers.add("A772648723");
		testPhoneNumbers.add("*614121112224");
		testPhoneNumbers.add("");
		testPhoneNumbers.add("+111 (202) 555-012533 ");

		
		for(String phoneNumber: testPhoneNumbers) {
			boolean actualResult = PhoneNumberValidator.isValid(phoneNumber, null);
			assert(!actualResult);
		}
		
	}
}
