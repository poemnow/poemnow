package com.cgm.poemnow.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cgm.poemnow.domain.book.Book;
import com.cgm.poemnow.domain.book.BookRequest;
import com.cgm.poemnow.domain.User;
import com.cgm.poemnow.domain.book.BookResponse;
import com.cgm.poemnow.service.book.BookService;

import org.apache.ibatis.jdbc.Null;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookService bookService;

	// 시집 전체 리스트
	@GetMapping(path = {"", "/"})
	public ResponseEntity<List<Book>> bookList() {
		return new ResponseEntity<>(bookService.findAllBooks(), HttpStatus.OK);
	}

	// 시집 추가(세션에 담긴 로그인한 유저 정보로 등록)
	@PostMapping({"", "/"}) // postman test 완!
	public ResponseEntity<Object> bookAdd(@RequestBody BookRequest bookRequest, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		if(user == null) {
			System.out.println("로그인 안 되어 있음 - 시집 추가 불가");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Book book = bookRequest.getBook();
		book.setUserId(user.getId());
		bookRequest.setBook(book);
		bookService.addBook(bookRequest);
		try {
			return new ResponseEntity<>("시집이 성공적으로 생성됐습니다.", HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 시집 수정
	@PutMapping("/{bookId}") // postman test 완!
	public ResponseEntity<?> bookModify(@RequestBody BookRequest bookRequest, @PathVariable String bookId, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		if(user == null){
			System.out.println("로그인 안 되어 있음 - 시집 수정  불가");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		bookRequest.getBook().setUserId(user.getId());
		bookRequest.getBook().setId(Integer.parseInt(bookId));
		return new ResponseEntity<>(bookService.modifyBook(bookRequest), HttpStatus.OK);
	}

	// 시집 삭제
	@DeleteMapping("/{bookId}") // 시-시집 연결도 함께 삭제됨! postman test 완!
	public ResponseEntity<?> bookRemove(@PathVariable("bookId") int bookId) {
		return new ResponseEntity<>(bookService.removeBook(bookId), HttpStatus.OK);
	}

	// 시집 상세보기
	@GetMapping("/{bookId}") // 시 리스트 - 시집 - 작가 정보 모두 반환됨! postman test 완!
	public ResponseEntity<BookResponse> bookDetail(@PathVariable("bookId") int bookId) {
		return new ResponseEntity<>(bookService.findBookById(bookId), HttpStatus.OK);
	}

	// 제목으로 시집 리스트
	@GetMapping(path = "/book/title") // postman test 완!
	public ResponseEntity<?> bookSearchByTitle(
			@RequestParam(value = "keyword") String keyword,
			@RequestParam(value = "sortOrder", required = false, defaultValue = "latest") String sortOrder
	){
		List<Book> response = bookService.findBooksByTitle(keyword, sortOrder);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 시집 별 시 목록
	@GetMapping(path = "/book-id/{id}") // postman test 완!
	public ResponseEntity<Object> poemListByBookId(@PathVariable("id") int id) {
		try {
			return new ResponseEntity<>(bookService.findPoemsByBookId(id), HttpStatus.OK);
		} catch (NullPointerException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	// 특정 유저의 시집 리스트
	@GetMapping(path = "/user-id/{id}") // postman test 완
	public ResponseEntity<?> bookListByUserId(@PathVariable("id") int id) {
		try {
			return new ResponseEntity<>(bookService.findPoemsByUserId(id), HttpStatus.OK);
		} catch (NullPointerException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	// 특정 유저가 좋아요 누른 시집 리스트 -> requestParam 있으면 특졍 유저, 아니면 내가 좋아요 누른 시집 리스트!
	@GetMapping(path = "/like") // postman test 완!
	public ResponseEntity<?> bookListLiked(
			HttpServletRequest request, @RequestParam(value="id", required = false, defaultValue = "0") int id) {
		try {
			HttpSession session = request.getSession();
			if(id==0){
				id = ((User)session.getAttribute("loginUser")).getId();
			}
			return new ResponseEntity<>(bookService.findBooksLiked(id), HttpStatus.OK);
		} catch (NullPointerException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

}