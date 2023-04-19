package com.cgm.poemnow.domain;

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
	private String author;
	private Timestamp createdAt;
	private short published;
	private Timestamp publishedAt;
	private String outerColor;
	private String innerColor;

}