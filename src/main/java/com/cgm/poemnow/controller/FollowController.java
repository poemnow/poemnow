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

	//팔로우 등록하기!
	@PostMapping(path = {"/", ""}) // postman test 완
	public ResponseEntity<?> followAdd(@RequestParam int followId,HttpServletRequest request){
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
//		int userId =1;

		int response = followService.addFollow(user.getId(), followId);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	//팔로우 취소
	@DeleteMapping(path = {"/", ""}) // postman test 완
	public ResponseEntity<?> followRemove(@RequestParam(value="followId") int followId,HttpServletRequest request){
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
//		int userId =1;
		int response = followService.removeFollow(user.getId(), followId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	//내가 팔로우한 사람 보기 - pathVariable 있으면 특정 사람이 팔로우한 사람 보기
	@GetMapping(path = {"/follow/{id}","/follow"})
	public ResponseEntity<?> followList(HttpServletRequest request,@PathVariable(required = false) String id) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");

		if(id != null){
			HashMap<String,List<HashMap<String, String>>> mp = new HashMap<>();
			List<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();
			response = followService.findYourFollowSame(Integer.parseInt(id), user.getId());
			mp.put("same" , response);
			response = followService.findYourFollowDif(Integer.parseInt(id), user.getId());
			mp.put("dif" , response);
			return new ResponseEntity<>(mp, HttpStatus.OK);
		}else{
			List<HashMap<String, String>> response = new ArrayList<HashMap<String, String>>();

			response = followService.findMyFollow(user.getId());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}


	}

	//나를 팔로우한 사람 보기 - pathVariable 있으면 특정 사람을 팔로우한 사람 보기
	@GetMapping(path = {"/follower/{id}","/follower"})
	public ResponseEntity<?> followerList(HttpServletRequest request,@PathVariable(required = false) String id) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		if(id != null){
			HashMap<String, List<HashMap<String, String>>> mp = new HashMap<>();
			List<HashMap<String, String>> response = followService.findYourFollowerSame(Integer.parseInt(id), user.getId());
			mp.put("same", response);
			response = followService.findYourFollowerDif(Integer.parseInt(id), user.getId());
			mp.put("dif", response);
			return new ResponseEntity<>(mp, HttpStatus.OK);
		}
		List<HashMap<String, String>> response = followService.findFollower(user.getId());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	//내 팔로우 수 구하기 - pathVariable 있으면 특정 사람의 팔로우 수 구하기
	@GetMapping(path = {"/followCnt/{id}","followCnt"})
	public ResponseEntity<Integer> followCnt(HttpServletRequest request,@PathVariable(required = false) String id) {
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId");
//		int userId = 1;
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