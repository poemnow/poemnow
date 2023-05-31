package com.cgm.poemnow.domain.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.cgm.poemnow.domain.Poem;
import com.cgm.poemnow.domain.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookLikeResponse {
	private Book book;
	private int bookLikeCount;
}
