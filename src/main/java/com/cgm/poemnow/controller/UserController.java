package com.cgm.poemnow.controller;


import com.cgm.poemnow.domain.User;
import com.cgm.poemnow.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<Boolean> userAdd(@RequestBody User userRequest) {
		boolean isSuccess = userService.addUser(userRequest);
		if (!isSuccess) {
			return new ResponseEntity<Boolean>( false, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<User> userLogin(@RequestBody User userRequest) {
		boolean isSuccess = userService.loginUser(userRequest);
		if (!isSuccess) {
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
		User loginedUser = userService.findUserById(userRequest);
		if (loginedUser == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<User>(loginedUser, HttpStatus.ACCEPTED);
	}

	@PostMapping("/logout")
	public ResponseEntity<User> userLogout(@RequestBody User userRequest) {
		boolean isSuccess = userService.logoutUser(userRequest);
		if (!isSuccess) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		User logoutedUser = userService.findUserById(userRequest);
		if (logoutedUser == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<User>(logoutedUser, HttpStatus.ACCEPTED);
	}

	@PostMapping("/withdraw")
	public ResponseEntity<User> userRemove(@RequestBody User userRequest) {
		boolean isSuccess = userService.removeUser(userRequest);
		if (!isSuccess) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		User withdrawedUser= userService.findUserById(userRequest);
		if (withdrawedUser == null) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<User>(withdrawedUser, HttpStatus.ACCEPTED);
	}

	@GetMapping("/profile")
	public ResponseEntity<User> userDetail(@RequestBody User userRequest) {
		User user = userService.findUserById(userRequest);
		if (user == null) {
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
	}

	@PostMapping("/profile")
	public ResponseEntity<User> userModify(@RequestBody User userRequest) {
		boolean isSuccess = userService.modifyUser(userRequest);
		if (!isSuccess) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		User modifiedUser = userService.findUserById(userRequest);
		return new ResponseEntity<User>(modifiedUser, HttpStatus.ACCEPTED);
	}

	// 소셜 로그인 추후 구현

	// 본인 인증 추후 구현
}
