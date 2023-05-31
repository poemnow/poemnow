package com.cgm.poemnow.service;

import java.util.List;

import com.cgm.poemnow.domain.book.Book;
public interface BookSearchService {

    List<Book> findBooksByTitle(String keyword, String sortOrder);

    List<Book> findBooksByPoem(String keyword, String sortOrder);

    List<Book> findBooksByAuthor(String keyword, String sortOrder);

}
