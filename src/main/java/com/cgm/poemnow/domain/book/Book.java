package com.cgm.poemnow.domain.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {

	private int id;
	private String title;
	private int userId;
	private Timestamp createdAt;
	private short published;
	private Timestamp publishedAt;
	private String outerColor;
	private String innerColor;
	private String bookDetail;

}