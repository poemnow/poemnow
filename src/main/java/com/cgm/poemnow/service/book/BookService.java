package com.cgm.poemnow.service.book;

import java.util.List;

import com.cgm.poemnow.domain.Book;
import com.cgm.poemnow.domain.BookRequest;
import com.cgm.poemnow.domain.Poem;

public interface BookService {

	int addBook(BookRequest bookRequest);

	List<Book> findAllBooks();

	List<Poem> findPoemsByBookId(int id);

	Book findBookById(int id);

	int modifyBook(BookRequest bookRequest);

	int removeBook(int id);

}