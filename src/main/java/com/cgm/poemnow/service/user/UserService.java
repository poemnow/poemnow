package com.cgm.poemnow.service.user;


import com.cgm.poemnow.domain.User;

public interface UserService {
	boolean addUser(User user);
	boolean loginUser(User user);
	boolean  isUserLoggedIn(User user);
	boolean logoutUser(User user);
	User findUserById(User user);
	boolean modifyUser(User user);
	boolean removeUser(User user);
	// + 비밀번호 찾기
	// + 본인 인증
}
