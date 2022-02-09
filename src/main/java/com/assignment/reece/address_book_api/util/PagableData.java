package com.assignment.reece.address_book_api.util;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PagableData<T> {
	private List<T> data;
	private long totalRecords;
	private int page;
	private int size;
}
