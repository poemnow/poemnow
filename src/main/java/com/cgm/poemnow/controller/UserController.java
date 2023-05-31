package com.cgm.poemnow.controller;

import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cgm.poemnow.domain.User;
import com.cgm.poemnow.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	// 회원가입
	@PostMapping({"", "/"}) // postman test 완!
	public ResponseEntity<Object> userAdd(@RequestBody User userRequest) {
		try {
			userService.addUser(userRequest);
			return new ResponseEntity<>("사용자가 성공적으로 생성됐습니다", HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 로그인
	@PostMapping("/login") // postman test 완
	public ResponseEntity<User> userLogin(@RequestBody User userRequest, HttpServletRequest request) {
		HttpSession session = request.getSession();
		boolean isSuccess = userService.loginUser(userRequest, session);
		System.out.println(isSuccess + "//////////");
		if (!isSuccess) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		User loginUser = userService.findUserByUserId(userRequest.getUserId());
		if (loginUser == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		System.out.println("loginUser: " + session.getAttribute("loginUser").toString());
		return new ResponseEntity<>(loginUser, HttpStatus.ACCEPTED);
	}

	// 로그아웃
	@PostMapping("/logout") // postman test 완
	public ResponseEntity<User> userLogout(@RequestBody User userRequest, HttpServletRequest request) {
		HttpSession session = request.getSession();
		boolean isSuccess = userService.logoutUser(userRequest, session);
		if (!isSuccess) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		User logoutedUser = userService.findUserByUserId(userRequest.getUserId());
		if (logoutedUser == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(logoutedUser, HttpStatus.ACCEPTED);
	}

	// 유저 수정
	@PutMapping ("/{id}") // postman test 완!
	public ResponseEntity<?> userModify(@RequestBody User userRequest) {
		try {
			userService.modifyUser(userRequest);
			return new ResponseEntity<>("사용자 정보 업데이트 성공", HttpStatus.ACCEPTED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 유저 탈퇴 - isActive 0으로 바꾸는 버전
//	@PatchMapping("/withdraw/{id}") // postman test 완!
//	public ResponseEntity<?> userRemove(@PathVariable("id") String id) {
//		try {
//			userService.removeUser(id);
//			return new ResponseEntity<>("사용자 탈퇴 성공", HttpStatus.ACCEPTED);
//		} catch (IllegalArgumentException e) {
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//		} catch (Exception e) {
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	// 유저 탈퇴 - delete 버전
	@DeleteMapping("/withdraw") // postman test 완!
	public ResponseEntity<?> userRemoveByDelete(HttpSession session, HttpServletRequest request) {

		User user = (User) session.getAttribute("loginUser");
		int id = user.getId();
		int deletedCnt = userService.removeUserByDelete(id);
		if(deletedCnt == 0){
			System.out.println("삭제 되지 않음. 다시 시도해주세요(삭제 결과 0)");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// 탈퇴했으므로 세션에서 지우기
		session.removeAttribute("loginUser");
		return new ResponseEntity<>("사용자 탈퇴 성공", HttpStatus.ACCEPTED);

	}


	// 유저 아이디로 상세보기
	@GetMapping("/user-id/{userId}") // postman test 완!
	public ResponseEntity<?> userDetail(@PathVariable("userId") String id) {
		try {
			User user = userService.findUserByUserId(id);
			return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 아이디로 상세보기(식별자)
	@GetMapping("/id/{id}") // postman test 완!
	public ResponseEntity<?> userDetailById(@PathVariable("id") int id) {
		try {
			User user = userService.findUserById(id);
			return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 닉네임으로 유저 리스트(검색)
	@GetMapping("/nickname") // postman test 완!
	public ResponseEntity<?> poemSearchByTitle(
			@RequestParam(value = "keyword") String keyword,
			@RequestParam(value = "sortOrder", required = false, defaultValue = "lastest") String sortOrder
	){
		List<User> response = userService.findUsersByNickname(keyword, sortOrder);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}