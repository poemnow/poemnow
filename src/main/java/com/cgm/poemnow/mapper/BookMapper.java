package com.cgm.poemnow.mapper;

import java.util.List;

import com.cgm.poemnow.domain.Book;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface BookMapper {

	int insertBook(Book book);

	List<Book> selectAllBooks();

}
