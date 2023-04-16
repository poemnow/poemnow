package com.cgm.poemnow.service.user;

import com.cgm.poemnow.domain.User;
import com.cgm.poemnow.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements  UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private HttpSession httpSession;

	@Override
	public void addUser(User user) {
		if (!checkId(user.getUserId())) {
			throw new IllegalArgumentException("잘못된 아이디입니다.");
		}

		if (!isValidId(user.getUserId())) {
			throw new IllegalArgumentException("중복된 아이디입니다.");
		}

		if (!checkPassword(user.getPassword())) {
			throw new IllegalArgumentException("잘못된 비밀번호입니다.");
		}

		if (!checkEmail(user.getEmail())) {
			throw new IllegalArgumentException("잘못된 이메일입니다.");
		}

		if (!isValidTimestamp(user.getUserBirth())) {
			throw new IllegalArgumentException("잘못된 생일정보입니다.");
		}

		if (user.getUserNickname().length() == 0) {
			throw new IllegalArgumentException("잘못된 닉네임입니다.");
		}

		userMapper.insertUser(user);
	}

	@Override
	public boolean loginUser(User user) {
		// 계정이 이미 로그인 된 사용자인지 확인
		User loggedInUser = userMapper.getLoggedInUser(user.getUserId());
		// 만약 loggedInUser가 존재한다면, 이미 로그인 한 계정이므로 로그인 시켜선 안 된다.
		if (loggedInUser != null) {
			return false;
		}

		// 계정 아이디와 비밀번호의 길이를 확인
		// 아이디가 [4, 20], 비밀번호가 [8, 20]이 아니라면 잘못된 입력이므로 false 반환
		// + 비밀번호 조건을 만족했는지도 확인해야 할듯
		if (user.getUserId().length() < 4 || user.getUserId().length() > 20 || user.getPassword().length() < 8 || 16 < user.getPassword().length()) {
			return false;
		}

		// + 비밀번호를 암호화 하는 과정 필요
		// 계정의 아이디와 비밀번호를 확인
		User loginUser = userMapper.selectUserByIdentifierAndPassword(
				user.getUserId(),
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
		httpSession.setAttribute("loginUser_" + loginUser.getUserId(), loginUser.getUserId());
		return true;
	}

	@Override
	public boolean isUserLoggedIn(User user) {
		String loginUserId = (String)httpSession.getAttribute("loginUser_" + user.getUserId());
		return loginUserId != null;
	}

	@Override
	public boolean logoutUser(User user) {
		String loginUserId = (String) httpSession.getAttribute("loginUser_" + user.getUserId());
		// 로그인 하지 않은 사용자거나, 요청을 보낸 사람과 로그 아웃 대상이 다른 경우 명령을 실행하지 않는다.
		if (loginUserId == null || loginUserId.equals(user.getUserId())) {
			return false;
		}
		httpSession.removeAttribute("loginUser_" + user.getUserId());
		return true;
	}

	@Override
	public User findUserById(String userId) {
		// 계정이 이미 로그인 된 사용자인지 확인
//		User loggedInUser = userMapper.getLoggedInUser(user.getUserId());
//		// 만약 loggedInUser가 존재한다면, 이미 로그인 한 계정이므로 로그인 시켜선 안 된다.
//		if (loggedInUser == null) {
//			return null;
//		}
		User userFound = userMapper.selectUserById((userId));
		if (userFound == null) {
			throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
		}
		return userFound;
	}

	@Override
	public void modifyUser(User user) {
//		// 계정이 이미 로그인 된 사용자인지 확인
//		User loggedInUser = userMapper.getLoggedInUser(user.getUserId());
//		// 만약 loggedInUser가 존재한다면, 이미 로그인 한 계정이므로 로그인 시켜선 안 된다.
//		if (loggedInUser == null) {
//			return false;
//		}
		if (userMapper.updateUser(user) == 0) {
			throw new IllegalArgumentException("잘못된 정보입니다.");
		}
	}

	@Override
	public void removeUser(String userId)  {
		if (userMapper.deleteUser(userId) == 0) {
			throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
		}
	}

	@Override
	public boolean checkId(String userId) {
		if (userId.length() < 4 || userId.length() > 20) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isValidId(String userId) {
		return userMapper.selectUserById(userId) == null;
	}

	@Override
	public boolean checkPassword(String password) {
		if (password.length() < 8 || password.length() > 20) {
			return false;
		}

		return true;
	}

	@Override
	public boolean checkEmail(String email) {
		String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher((email));
		return matcher.matches();
	}

	@Override
	public boolean isValidTimestamp(Timestamp timestamp) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateFormat.format(timestamp);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
