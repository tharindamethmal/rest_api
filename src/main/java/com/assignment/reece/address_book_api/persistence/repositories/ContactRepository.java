package com.assignment.reece.address_book_api.persistence.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.assignment.reece.address_book_api.persistence.entities.Contact;

public interface ContactRepository extends PagingAndSortingRepository<Contact, Integer> {

	List<Contact> findAllByAddressBook_Id(Integer addressBookId);

	@Query("SELECT DISTINCT d.name FROM Contact d")
	Page<String> findDistinctContactNames(PageRequest pageable);

	List<Contact> findByNameIn(List<String> uniqueContactNames);

}
