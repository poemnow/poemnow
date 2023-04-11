package com.cgm.poemnow.domain;

import lombok.Data;

@Data
public class Follow {
	private int id;
	private int userId;
	private int followId;
	private String createdAt;

}
