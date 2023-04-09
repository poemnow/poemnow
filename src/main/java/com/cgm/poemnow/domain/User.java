package com.cgm.poemnow.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private int id;
	private String user_id;
	private String user_nickname;
	private String user_name;
	private Timestamp user_birth;
	private String email;
	private String user_phone;
	private String password;
	private String profile_pic;
	private String bio;
	private String social_url;
	private Timestamp created_at;
	private Timestamp updated_at;
	private int follower_cnt;
	private int following_cnt;
	private boolean active;
}
