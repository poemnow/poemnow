package com.cgm.poemnow.mapper;

import com.cgm.poemnow.domain.book.Book;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface  BookSearchMapper {

    List<Book> selectBooksByTitle(String keyword, String sortOrder);

    List<Book> selectBooksByPoem(String keyword, String sortOrder);

    List<Book> selectBooksByAuthor(String keyword, String sortOrder);

}
