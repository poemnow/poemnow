package com.cgm.poemnow.controller;

import java.util.List;

import com.cgm.poemnow.domain.Book;
import com.cgm.poemnow.domain.BookRequest;
import com.cgm.poemnow.service.book.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookService bookService;

	@PostMapping("/bookAdd")
	public ResponseEntity<Object> bookAdd(@RequestBody BookRequest bookRequest) {
		bookService.addBook(bookRequest);
		try {
			return new ResponseEntity<>("시집이 성공적으로 생성됐습니다.", HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/bookList")
	public ResponseEntity<List<Book>> bookList() {
		return new ResponseEntity<>(bookService.findAllBooks(), HttpStatus.OK);
	}

	@GetMapping(path = "/poemListByBookId/{id}")
	public ResponseEntity<Object> poemListByBookId(@PathVariable("id") int id) {
		try {
			return new ResponseEntity<>(bookService.findPoemsByBookId(id), HttpStatus.OK);
		} catch (NullPointerException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/bookDetail/{id}")
	public ResponseEntity<Book> bookDetail(@PathVariable("id") int id) {
		return new ResponseEntity<>(bookService.findBookById(id), HttpStatus.OK);
	}

	@PutMapping("/bookModify")
	public ResponseEntity<?> bookModify(@RequestBody BookRequest bookRequest) {
		return new ResponseEntity<>(bookService.modifyBook(bookRequest), HttpStatus.OK);
	}

	@DeleteMapping("/bookRemove/{id}")
	public ResponseEntity<?> bookRemove(@PathVariable("id") int id) {
		return new ResponseEntity<>(bookService.removeBook(id), HttpStatus.OK);
	}

}