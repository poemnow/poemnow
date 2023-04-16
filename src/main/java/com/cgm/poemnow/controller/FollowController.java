package com.cgm.poemnow.controller;

import com.cgm.poemnow.domain.Follow;
import com.cgm.poemnow.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/follow")
public class FollowController {
	@Autowired
	private FollowService followService;

	//팔로우 등록하기!
	@PostMapping(path = "/followAdd")
	public ResponseEntity<?> followAdd(@RequestBody Follow followRequest){
		int response = followService.addFollow(followRequest);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	//팔로우 취소
	@DeleteMapping(path = "/followRemove")
	public ResponseEntity<?> followRemove(@RequestParam int followId){
		int userId = 1; // httpsesion에서 받아오는 아이디
		int response = followService.removeFollow(userId, followId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	//내가 팔로우 한 사람 보기
	@GetMapping(path = "/followList")
	public ResponseEntity<List<HashMap<String, String>>> followList() {
		int userId = 1; // httpsesion에서 받아오는 아이디
		List<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();

		response = followService.findFollow(userId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	//나를 팔로워 한 사람 보기
	@GetMapping(path = "/followerList")
	public ResponseEntity<List<HashMap<String, String>>> followerList() {
		int userId = 3; // httpsesion에서 받아오는 아이디
		List<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();
		response = followService.findFollower(userId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	//내 팔로우 수 구하기
	@GetMapping(path = "/followCnt")
	public ResponseEntity<Integer> followCnt() {
		int userId = 1; // httpsesion에서 받아오는 아이디
		int response = 0;
		response = followService.findFollowCnt(userId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	//내 팔로워 수 구하기
	@GetMapping(path = "/followerCnt")
	public ResponseEntity<Integer> followerCnt() {
		int userId = 2; // httpsesion에서 받아오는 아이디
		int response = 0;
		response = followService.findFollowerCnt(userId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
