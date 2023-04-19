package com.cgm.poemnow.service.book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cgm.poemnow.domain.Book;
import com.cgm.poemnow.domain.BookPoem;
import com.cgm.poemnow.domain.BookRequest;
import com.cgm.poemnow.domain.Poem;
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
	public List<Book> findAllBooks() {
		return bookMapper.selectAllBooks();
	}

	@Override
	public List<Poem> findPoemsByBookId(int id) { return bookMapper.selectPoemsByBookId(id); }

	@Override
	public Book findBookById(int id) { return bookMapper.selectBookById(id); }

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

}
