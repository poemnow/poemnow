package com.cgm.poemnow.controller;

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

	@PostMapping("/register")
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

	@PostMapping("/login")
	public ResponseEntity<User> userLogin(@RequestBody User userRequest) {
		boolean isSuccess = userService.loginUser(userRequest);
		if (!isSuccess) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		User loginedUser = userService.findUserById(userRequest.getUserId());
		if (loginedUser == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(loginedUser, HttpStatus.ACCEPTED);
	}

	@PostMapping("/logout")
	public ResponseEntity<User> userLogout(@RequestBody User userRequest) {
		boolean isSuccess = userService.logoutUser(userRequest);
		if (!isSuccess) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		User logoutedUser = userService.findUserById(userRequest.getUserId());
		if (logoutedUser == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(logoutedUser, HttpStatus.ACCEPTED);
	}

	@PostMapping("/withdraw/{id}")
	public ResponseEntity<?> userRemove(@PathVariable("id") String id) {
		try {
			userService.removeUser(id);
			return new ResponseEntity<>("사용자 탈퇴 성공", HttpStatus.ACCEPTED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/profile/{id}")
	public ResponseEntity<?> userDetail(@PathVariable("id") String id) {
		try {
			User user = userService.findUserById(id);
			return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/profile")
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

	// 소셜 로그인 추후 구현

	// 본인 인증 추후 구현

}