package com.cgm.poemnow.service;

import java.util.HashMap;
import java.util.List;

import com.cgm.poemnow.domain.Book;
public interface BookSearchService {

    List<HashMap<String, ?>>  findBooksByTitle(String keyword, String sortOrder);

    List<Book> findBooksByPoem(String keyword, String sortOrder);

    List<Book> findBooksByAuthor(String keyword, String sortOrder);

}
