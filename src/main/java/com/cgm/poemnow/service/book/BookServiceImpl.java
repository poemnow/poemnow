package com.cgm.poemnow.service.book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cgm.poemnow.domain.book.Book;
import com.cgm.poemnow.domain.BookPoem;
import com.cgm.poemnow.domain.book.BookLikeResponse;
import com.cgm.poemnow.domain.book.BookRequest;
import com.cgm.poemnow.domain.Poem;
import com.cgm.poemnow.domain.book.BookResponse;
import com.cgm.poemnow.mapper.BookLikeMapper;
import com.cgm.poemnow.mapper.BookMapper;
import com.cgm.poemnow.mapper.PoemMapper;
import com.cgm.poemnow.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookServiceImpl implements BookService {

	@Autowired
	private BookMapper bookMapper;

	@Autowired
	private PoemMapper poemMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private BookLikeMapper bookLikeMapper;

	@Override
	public List<Book> findAllBooks() {
		return bookMapper.selectAllBooks();
	}

	@Override
	public int addBook(BookRequest bookRequest) {
		Book book = bookRequest.getBook();
		List<BookPoem> list = bookRequest.getBookPoemList();

		bookMapper.insertBook(book);
		int bookId = bookMapper.selectRecentBook();
		Map<String, Object> map = new HashMap<>();

		// list 안에 있는 bookPoem 객체의 요소 bookId에 최근에 들어간 book의 id 넣어줌
		for (BookPoem bp : list) {
			bp.setBookId(bookId);
		}
		map.put("list", list);

		return bookMapper.insertBookPoem(map);
	}

	@Override
	public List<Poem> findPoemsByBookId(int id) { return bookMapper.selectPoemsByBookId(id); }

	@Override
	public int modifyBook(BookRequest bookRequest) {
		Book book = bookRequest.getBook();
		List<BookPoem> bookPoemList = bookRequest.getBookPoemList();

		Map<String, Object> map = new HashMap<>();
		map.put("list", bookPoemList);

		bookMapper.updateBook(book);
		// 해당하는 북 포엠 다 지우고
		bookMapper.deleteBookPoem(book);
		// 새로 다시 담기
		return bookMapper.insertBookPoem(map);
	}

	@Override
	public int removeBook(int id) {
		Book book = new Book();
		book.setId(id);
		bookMapper.deleteBookPoem(book);
		return bookMapper.deleteBook(id);
	}

	@Override
	public BookResponse findBookById(int id) {
		BookResponse bookResponse = new BookResponse();
		bookResponse.setBook(bookMapper.selectBookById(id));
		bookResponse.setPoemList(poemMapper.selectPoemsByBookId(id));
		bookResponse.setUser(userMapper.selectUserById(bookResponse.getBook().getUserId()));
		return bookResponse;
	}

	@Override
	public List<Book> findBooksByTitle(String keyword, String sortOrder) {
		return bookMapper.selectBooksByTitle(keyword, sortOrder);
	}

	@Override
	public List<BookLikeResponse> findPoemsByUserId(int id) {
		List<BookLikeResponse> bookLikeResponseList = new ArrayList<>();
//		for (Book blr: bookMapper.selectBooksByUserId(id)) {
//			int bookId = blr.getId();
//
//		}
//		return bookMapper.selectBooksByUserId(id);
		return bookLikeResponseList;
	}

	@Override
	public List<BookLikeResponse> findBooksLiked(int userId) {
		List<BookLikeResponse> bookLikeResponseList = new ArrayList<>();
//		return bookMapper.selectBooksLiked(userId);
		return bookLikeResponseList;
	}

}
