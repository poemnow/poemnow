package com.cgm.poemnow.controller;

import java.util.List;

import com.cgm.poemnow.domain.Book;
import com.cgm.poemnow.service.book.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {
	@Autowired
	private BookService bookService;

	@PostMapping("/bookAdd")
	public ResponseEntity<?> bookAdd(@RequestBody Book bookRequest) {
		int response = bookService.bookAdd(bookRequest);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping(path = "/bookList")
	public ResponseEntity<List<Book>> bookList() {
		List<Book> response = bookService.findAllBooks();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}