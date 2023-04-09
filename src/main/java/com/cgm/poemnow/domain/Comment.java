package com.cgm.poemnow.domain;

import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

	private int id;
	private String content;
	private int userId;
	private int poemId;
	private String createdAt;
	private String updatedAt;
	private boolean deleted;

}
