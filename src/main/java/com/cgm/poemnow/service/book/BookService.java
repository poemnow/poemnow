package com.cgm.poemnow.service.book;

import java.util.List;

import com.cgm.poemnow.domain.Book;

public interface BookService {

	int bookAdd(Book book);

	List<Book> findAllBooks();

}
