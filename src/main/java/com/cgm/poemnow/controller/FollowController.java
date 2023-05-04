package com.cgm.poemnow.controller;

import com.cgm.poemnow.domain.Follow;
import com.cgm.poemnow.domain.User;
import com.cgm.poemnow.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/follow")
public class FollowController {

	@Autowired
	private FollowService followService;

	@PostMapping("login") // postman test success
	public ResponseEntity<?> login(@RequestBody User user, HttpServletRequest request){
		boolean isSuccess = followService.loginUser(user, request);
		return new ResponseEntity<>(isSuccess, HttpStatus.ACCEPTED);
	}

	//팔로우 등록하기!
	@PostMapping(path = "/followAdd") // postman test success
	public ResponseEntity<?> followAdd(@RequestBody Follow followRequest, HttpServletRequest request){
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		followRequest.setUserId(user.getId());
		if(user.getId() == followRequest.getFollowId()){
			// 팔로우 하는 유저 아이디(주요키)랑 세션에 로그인된 유저 아이디(주요키)랑 같을 때
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		int response = followService.addFollow(followRequest);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	//팔로우 취소
	@DeleteMapping(path = "/followRemove") // postman test success
	public ResponseEntity<?> followRemove(@RequestParam int followId,HttpServletRequest request){
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		Follow follow = new Follow();
		follow.setFollowId(followId);
		follow.setUserId(user.getId());
		int response = followService.removeFollow(follow);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	//내가 팔로우 한 사람 보기
	@GetMapping(path = {"/followList/{id}","followList"}) // postman test success
	public ResponseEntity<List<HashMap<String, String>>> followList(HttpServletRequest request,@PathVariable(required = false) String id) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		if(id != null){
			user.setId(Integer.parseInt(id));
		}
		List<HashMap<String, String>> response = followService.findFollow(user.getId());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	//나를 팔로워 한 사람 보기
	@GetMapping(path = {"/followerList/{id}","followerList"}) // postman test success
	public ResponseEntity<List<HashMap<String, String>>> followerList(HttpServletRequest request,@PathVariable(required = false) String id) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		if(id != null){
			user.setId(Integer.parseInt(id));
		}
		List<HashMap<String, String>> response = followService.findFollower(user.getId());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	//내 팔로우 수 구하기
	@GetMapping(path = {"/followCnt/{id}","followCnt"}) // postman test success
	public ResponseEntity<Integer> followCnt(HttpServletRequest request,@PathVariable(required = false) String id) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		if(id != null){
			user.setId(Integer.parseInt(id));
		}
		int response = followService.findFollowCnt(user.getId());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	//내 팔로워 수 구하기
	@GetMapping(path = {"/followerCnt/{id}","followerCnt"}) // postman test success
	public ResponseEntity<Integer> followerCnt(HttpServletRequest request,@PathVariable(required = false) String id) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		if(id != null){
			user.setId(Integer.parseInt(id));
		}
		int response = followService.findFollowerCnt(user.getId());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
