package com.cgm.poemnow.mapper;

import java.util.List;
import java.util.Map;

import com.cgm.poemnow.domain.book.Book;
import com.cgm.poemnow.domain.Poem;
import com.cgm.poemnow.domain.book.BookLikeResponse;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface BookMapper {

	List<Book> selectAllBooks();

	int insertBook(Book book);

	int insertBookPoem(Map map);

	List<Poem> selectPoemsByBookId(int id);

	Book selectBookById(int id);

	int selectRecentBook();

	int updateBook(Book book);

	int deleteBook(int id);

	int deleteBookPoem(Book book);

	List<Book> selectBooksByTitle(String keyword, String sortOrder);

	List<Book> selectBooksByUserId(int id);

	List<Book> selectBooksLiked(int userId);
}
