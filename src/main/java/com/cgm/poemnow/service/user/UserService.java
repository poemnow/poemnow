package com.cgm.poemnow.service.user;

import com.cgm.poemnow.domain.User;

import java.sql.Timestamp;

public interface UserService {

	void addUser(User user);
	boolean loginUser(User user);
	boolean  isUserLoggedIn(User user);
	boolean logoutUser(User user);
	User findUserById(String userId);
	void modifyUser(User user);
	void removeUser(String userId);
	// + 비밀번호 찾기
	// + 본인 인증
	boolean checkId(String userId);
	boolean isValidId(String userId);
	boolean checkPassword(String userId);
	boolean checkEmail(String email);
	boolean isValidTimestamp(Timestamp timestamp);

}