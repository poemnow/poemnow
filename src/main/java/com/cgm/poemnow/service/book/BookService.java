package com.cgm.poemnow.service.book;

import java.util.List;

import com.cgm.poemnow.domain.book.Book;
import com.cgm.poemnow.domain.book.BookLikeResponse;
import com.cgm.poemnow.domain.book.BookRequest;
import com.cgm.poemnow.domain.Poem;
import com.cgm.poemnow.domain.book.BookResponse;

public interface BookService {

	List<Book> findAllBooks();

	int addBook(BookRequest bookRequest);

	List<Poem> findPoemsByBookId(int id);

	int modifyBook(BookRequest bookRequest);

	int removeBook(int id);

	BookResponse findBookById(int id);

	List<Book> findBooksByTitle(String keyword, String sortOrder);

	List<BookLikeResponse> findPoemsByUserId(int id);

	List<BookLikeResponse> findBooksLiked(int userId);
}