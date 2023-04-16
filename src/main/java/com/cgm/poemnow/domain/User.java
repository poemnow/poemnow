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
	private String userId;
	private String userNickname;
	private String userName;
	private Timestamp userBirth;
	private String email;
	private String userPhone;
	private String password;
	private String profilePic;
	private String bio;
	private String socialUrl;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private int followerCnt;
	private int followingCnt;
	private boolean isActive;
	private boolean isLoggedIn;

}