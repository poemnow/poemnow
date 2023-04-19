package com.cgm.poemnow.mapper;

import java.util.List;
import java.util.Map;

import com.cgm.poemnow.domain.Book;
import com.cgm.poemnow.domain.Poem;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface BookMapper {

	int insertBook(Book book);

	int insertBookPoem(Map map);

	List<Book> selectAllBooks();

	List<Poem> selectPoemsByBookId(int id);

	Book selectBookById(int id);

	int selectRecentBook();

	int updateBook(Book book);

	int deleteBook(int id);

	int deleteBookPoem(Book book);

}
