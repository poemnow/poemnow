package com.cgm.poemnow.mapper;

import com.cgm.poemnow.domain.Book;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface  BookSearchMapper {

    List<HashMap<String, ?>>  selectBooksByTitle(String keyword, String sortOrder);

    List<Book> selectBooksByPoem(String keyword, String sortOrder);

    List<Book> selectBooksByAuthor(String keyword, String sortOrder);

}
