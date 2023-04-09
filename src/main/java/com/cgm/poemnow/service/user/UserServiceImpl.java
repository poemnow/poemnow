package com.cgm.poemnow.service.user;

import com.cgm.poemnow.domain.User;
import com.cgm.poemnow.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements  UserService {
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private HttpSession httpSession;

	@Override
	public boolean addUser(User user) {
		int numsInserted = userMapper.insertUser(user);
		return numsInserted > 0;
	}

	@Override
	public boolean loginUser(User user) {
		// 계정이 이미 로그인 된 사용자인지 확인
		User loggedInUser = userMapper.getLoggedInUser(user.getUser_id());
		// 만약 loggedInUser가 존재한다면, 이미 로그인 한 계정이므로 로그인 시켜선 안 된다.
		if (loggedInUser != null) {
			return false;
		}

		// 계정 아이디와 비밀번호의 길이를 확인
		// 아이디가 [4, 20], 비밀번호가 [8, 20]이 아니라면 잘못된 입력이므로 false 반환
		// + 비밀번호 조건을 만족했는지도 확인해야 할듯
		if (user.getUser_id().length() < 4 || user.getUser_id().length() > 20 || user.getPassword().length() < 8 || 16 < user.getPassword().length()) {
			return false;
		}

		// + 비밀번호를 암호화 하는 과정 필요
		// 계정의 아이디와 비밀번호를 확인
		User loginUser = userMapper.selectUserByIdentifierAndPassword(
				user.getUser_id(),
				user.getPassword()
		);

		if (loginUser == null) {
			return false;
		}


		// 사용자 정보를 서명합니다.
//		String signedUser = cookieService.signUser(loginedUser);
//
//		// 서명된 쿠키를 생성합니다.
//		Cookie userCookie = new Cookie("user", signedUser);
//
//		// 쿠키의 유효기간을 설정합니다.
//		userCookie.setMaxAge(60 * 60 * 24 * 7);
//
//		// 생성된 쿠키를 HTTP 응답에 추가합니다.
//		response.addCookie(userCookie);

		// 여러 계정의 로그인을 위해 세션에 사용자 id를 추가해서 저장
		// 아이디는 이미 공개된 정보라 세션 key로 사용해도 괜찮을듯
		httpSession.setAttribute("loginUser_" + loginUser.getUser_id(), loginUser.getUser_id());
		return true;
	}

	@Override
	public boolean isUserLoggedIn(User user) {
		String loginUserId = (String)httpSession.getAttribute("loginUser_" + user.getUser_id());
		return loginUserId != null;
	}


	@Override
	public boolean logoutUser(User user) {
		String loginUserId = (String) httpSession.getAttribute("loginUser_" + user.getUser_id());
		// 로그인 하지 않은 사용자거나, 요청을 보낸 사람과 로그 아웃 대상이 다른 경우 명령을 실행하지 않는다.
		if (loginUserId == null || loginUserId.equals(user.getUser_id())) {
			return false;
		}
		httpSession.removeAttribute("loginUser_" + user.getUser_id());
		return true;
	}

	@Override
	public User findUserById(User user) {
		// 계정이 이미 로그인 된 사용자인지 확인
		User loggedInUser = userMapper.getLoggedInUser(user.getUser_id());
		// 만약 loggedInUser가 존재한다면, 이미 로그인 한 계정이므로 로그인 시켜선 안 된다.
		if (loggedInUser == null) {
			return null;
		}

		return userMapper.selectUserById(user.getUser_id());
	}

	@Override
	public boolean modifyUser(User user) {
		// 계정이 이미 로그인 된 사용자인지 확인
		User loggedInUser = userMapper.getLoggedInUser(user.getUser_id());
		// 만약 loggedInUser가 존재한다면, 이미 로그인 한 계정이므로 로그인 시켜선 안 된다.
		if (loggedInUser == null) {
			return false;
		}

		return userMapper.updateUser(user) > 0;
	}

	@Override
	public boolean removeUser(User user) {
		return userMapper.deleteUser(user) > 0;
	}
}
