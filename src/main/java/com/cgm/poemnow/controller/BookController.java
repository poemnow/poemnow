package com.cgm.poemnow.controller;

import java.util.List;

import com.cgm.poemnow.domain.Book;
import com.cgm.poemnow.service.book.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {
	@Autowired
	private BookService bookService;

	@PostMapping("/bookAdd")
	public ResponseEntity<?> bookAdd(@RequestBody Book bookRequest) {
		int response = bookService.addBook(bookRequest);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping(path = "/bookList")
	public ResponseEntity<List<Book>> bookList() {
		List<Book> response = bookService.findAllBooks();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/bookDetail/{id}")
	public ResponseEntity<Book> bookDetail(@PathVariable("id") int id) {
		Book response = bookService.findBookById(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// todo book과 poem 연결 테이블까지 적용하는 로직 넣기
	@PatchMapping("/bookModify")
	public ResponseEntity<?> bookModify(@RequestParam int id, @RequestParam String title) {
		Book book = Book.builder().id(id).title(title).build();
		int response = bookService.modifyBook(book);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/bookRemove/{id}")
	public ResponseEntity<?> bookRemove(@PathVariable("id") int id) {
		int response = bookService.removeBook(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}