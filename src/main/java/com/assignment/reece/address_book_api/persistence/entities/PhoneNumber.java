package com.assignment.reece.address_book_api.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.assignment.reece.address_book_api.util.PhoneNumberType;
import com.assignment.reece.address_book_api.util.ValidPhoneNumber;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "phone_numbers")
@NoArgsConstructor
public class PhoneNumber {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	private PhoneNumberType type;

	@NotNull
	@NotEmpty
	@ValidPhoneNumber
	private String number;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "contact_id", nullable = false)
	private Contact contact;

	public PhoneNumber(PhoneNumberType type, String number, Contact contact) {
		this.type = type;
		this.number = number;
		this.contact = contact;
	}

	public PhoneNumber(PhoneNumberType type, String number) {
		this.type = type;
		this.number = number;
	}
}
