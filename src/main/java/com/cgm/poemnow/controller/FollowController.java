package com.cgm.poemnow.controller;

import com.cgm.poemnow.domain.User;
import com.cgm.poemnow.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/follow")
@CrossOrigin("*")
public class FollowController {

	@Autowired
	private FollowService followService;

	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody User user, HttpServletRequest request){
		boolean isSuccess = followService.loginUser(user, request);
		return new ResponseEntity<>(isSuccess, HttpStatus.ACCEPTED);
	}

	//팔로우 등록하기!
	@PostMapping(path = "/followAdd")
	public ResponseEntity<?> followAdd(@RequestParam int followId,HttpServletRequest request){
		//HttpSession session = request.getSession();
		//int userId = (int) session.getAttribute("userId");
		int userId =1;

		int response = followService.addFollow(userId, followId);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	//팔로우 취소
	@DeleteMapping(path = "/followRemove")
	public ResponseEntity<?> followRemove(@RequestParam int followId,HttpServletRequest request){
		//HttpSession session = request.getSession();
		//int userId = (int) session.getAttribute("userId");
		int userId =1;
		int response = followService.removeFollow(userId, followId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	//내가 팔로우 한 사람 보기
	@GetMapping(path = {"/followList/{id}","followList"})
	public ResponseEntity<?> followList(HttpServletRequest request,@PathVariable(required = false) String id) {
		//	HttpSession session = request.getSession();
		//int userId = (int) session.getAttribute("userId");
		int userId =1;
		if(id != null){
			HashMap<String,List<HashMap<String, String>>> mp = new HashMap<>();
			List<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();
			response = followService.findYourFollowSame(Integer.parseInt(id), userId);
			mp.put("same" , response);
			response = followService.findYourFollowDif(Integer.parseInt(id), userId);
			mp.put("dif" , response);
			return new ResponseEntity<>(mp, HttpStatus.OK);
		}else{
			List<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();

			response = followService.findMyFollow(userId);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}


	}

	//나를 팔로워 한 사람 보기
	@GetMapping(path = {"/followerList/{id}","followerList"})
	public ResponseEntity<List<HashMap<String, String>>> followerList(HttpServletRequest request,@PathVariable(required = false) String id) {
		//HttpSession session = request.getSession();
		//int userId = (int) session.getAttribute("userId");
		int userId =1;
		if(id != null){
			userId = Integer.parseInt(id);
		}
		List<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();
		response = followService.findFollower(userId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}





	//내 팔로우 수 구하기
	@GetMapping(path = {"/followCnt/{id}","followCnt"})
	public ResponseEntity<Integer> followCnt(HttpServletRequest request,@PathVariable(required = false) String id) {
		//HttpSession session = request.getSession();
		//int userId = (int) session.getAttribute("userId");
		int userId = 1;
		if(id != null){
			userId = Integer.parseInt(id);
		}
		int response = followService.findFollowCnt(userId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	//내 팔로워 수 구하기
	@GetMapping(path = {"/followerCnt/{id}","followerCnt"})
	public ResponseEntity<Integer> followerCnt(HttpServletRequest request,@PathVariable(required = false) String id) {
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId");
		if(id != null){
			userId = Integer.parseInt(id);
		}
		int response = followService.findFollowerCnt(userId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
