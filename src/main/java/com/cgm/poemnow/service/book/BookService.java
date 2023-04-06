package com.cgm.poemnow.service.book;

import java.util.List;

import com.cgm.poemnow.domain.Book;

public interface BookService {

	int addBook(Book book);

	List<Book> findAllBooks();

	Book findBookById(int id);

	int modifyBook(Book book);

	int removeBook(int id);

}
