package com.assignment.reece.address_book_api.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import com.assignment.reece.address_book_api.persistence.entities.AddressBook;

public interface AddressBookRepository extends CrudRepository<AddressBook, Integer> {

	boolean existsByName(String name);

}
