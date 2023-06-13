package com.cgm.poemnow.domain.response;

import lombok.Data;

import com.cgm.poemnow.domain.Poem;
import com.cgm.poemnow.domain.User;

@Data
public class PoemSearchResponse {
	private Poem poem;
	private User writer;
	private int commentCnt;
}
