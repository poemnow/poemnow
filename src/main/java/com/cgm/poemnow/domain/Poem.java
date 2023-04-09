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
	private int author_id;
	private String created_at;
	private String updated_at;
	private boolean published;
	private String published_at;
	private int like_cnt;
	private int view_cnt;
	private boolean unknown;
	private String font;
}
