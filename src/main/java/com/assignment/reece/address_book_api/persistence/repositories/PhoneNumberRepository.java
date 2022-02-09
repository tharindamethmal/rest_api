package com.assignment.reece.address_book_api.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import com.assignment.reece.address_book_api.persistence.entities.PhoneNumber;

public interface PhoneNumberRepository extends CrudRepository<PhoneNumber, Integer> {

}
