package com.cgm.poemnow.domain;

import lombok.Data;

@Data
public class Poem {

	private int id;
	private String title;
	private String content;
	private String authorId;
	private String createdAt;
	private String updatedAt;
	private boolean published;
	private String publishedAt;
	private int likeCnt;
	private int viewCnt;
	private boolean unknown;

}
