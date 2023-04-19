package com.cgm.poemnow.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookPoem {

	private int bookId;
	private int poemId;
	private int poemOrder;

}