package com.cgm.poemnow.domain.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.cgm.poemnow.domain.BookPoem;
import com.cgm.poemnow.domain.book.Book;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {

	private Book book;
	private List<BookPoem> bookPoemList;

}