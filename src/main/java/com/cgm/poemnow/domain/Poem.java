package com.cgm.poemnow.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Poem {

	private int id;
	private String title;
	private String content;
	private int authorId;
	private String createdAt;
	private String updatedAt;
	private boolean published;
	private String publishedAt;
	private int likeCnt;
	private int viewCnt;
	private boolean unknown;
	private String font;

}
