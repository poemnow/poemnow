package com.cgm.poemnow.service;

import com.cgm.poemnow.domain.book.Book;
import com.cgm.poemnow.mapper.BookSearchMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookSearchServiceImpl implements BookSearchService {

    @Autowired
    private BookSearchMapper bookSearchMapper;

    @Override
    public List<Book> findBooksByTitle(String keyword, String sortOrder) {
        return bookSearchMapper.selectBooksByTitle(keyword, sortOrder);
    }

    @Override
    public List<Book> findBooksByPoem(String keyword, String sortOrder) {
        return bookSearchMapper.selectBooksByPoem(keyword, sortOrder);
    }

    @Override
    public List<Book> findBooksByAuthor(String keyword, String sortOrder) {
        return bookSearchMapper.selectBooksByAuthor(keyword, sortOrder);
    }
}
