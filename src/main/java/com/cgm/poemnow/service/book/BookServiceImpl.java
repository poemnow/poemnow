package com.cgm.poemnow.service.book;

import java.util.List;

import com.cgm.poemnow.domain.Book;
import com.cgm.poemnow.mapper.BookMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookServiceImpl implements BookService {

	@Autowired
	private BookMapper bookMapper;

	@Override
	public int addBook(Book book) {
		return bookMapper.insertBook(book);
	}

	@Override
	public List<Book> findAllBooks() {
		return bookMapper.selectAllBooks();
	}

	@Override
	public Book findBookById(int id) { return bookMapper.selectBookById(id); }

	@Override
	public int modifyBook(Book book) { return bookMapper.updateBook(book); }

	@Override
	public int removeBook(int id) {
		return bookMapper.deleteBook(id);
	}

}
