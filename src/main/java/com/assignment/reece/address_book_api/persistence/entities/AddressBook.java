package com.assignment.reece.address_book_api.persistence.entities;

import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "address_books")
@NoArgsConstructor
public class AddressBook {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@NotEmpty
	@Column(unique = true)
	private String name;

	@OneToMany(mappedBy = "addressBook", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Contact> contacts;

	@CreationTimestamp
	@Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE", nullable = false, updatable = false)
	private OffsetDateTime createdAt;

	public AddressBook(String name, List<Contact> contacts) {
		this.name = name;
		this.contacts = contacts;
	}

}
