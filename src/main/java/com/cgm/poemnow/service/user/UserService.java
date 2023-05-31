package com.cgm.poemnow.service.user;

import com.cgm.poemnow.domain.User;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpSession;

public interface UserService {

	void addUser(User user);
	boolean loginUser(User user, HttpSession session);
	boolean convertLoggedIn(User user);
	boolean  isUserLoggedIn(User user);
	boolean logoutUser(User user, HttpSession session);
	User findUserByUserId(String userId);
	User findUserById(int id);
	void modifyUser(User user);
	void removeUser(String userId);
	// + 비밀번호 찾기
	// + 본인 인증
	boolean checkId(String userId);
	boolean isValidId(String userId);
	boolean checkPassword(String userId);
	boolean checkEmail(String email);
	boolean isValidTimestamp(Timestamp timestamp);

	List<User> findUsersByNickname(String keyword, String sortOrder);

	int removeUserByDelete(int id);

}