package com.cgm.poemnow.controller;

import com.cgm.poemnow.domain.Poem;
import com.cgm.poemnow.domain.User;
import com.cgm.poemnow.service.poem.PoemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/poem")
public class PoemController {

	@Autowired
	private PoemService poemService;

	// 시 전체 리스트
	@GetMapping(path = {"", "/"}) // postman test 완!
	public ResponseEntity<List<Poem>> poemList() {
		List<Poem> response = poemService.findAllPoems();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 시 등록(세션에 담긴 로그인한 유저 정보로 등록)
	@PostMapping({"", ","}) // postman test 완!
	public ResponseEntity<?> poemAdd(@RequestBody Poem poemRequest, HttpServletRequest request){
		HttpSession session = request.getSession();
		if (session.getAttribute("loginUser")==null){
			System.out.println("로그인 안 되어 있음 - 시 등록 불가");
			return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		poemRequest.setUserId(((User)session.getAttribute("loginUser")).getId());
		int response = poemService.addPoem(poemRequest);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/{poemId}") // postman test 완!
	public ResponseEntity<?> poemModify(@RequestBody Poem poemRequest, @PathVariable String poemId, HttpServletRequest request){
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		if(user == null) {
			System.out.println("로그인 후 시를 수정할 수 있습니다.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		poemRequest.setUserId(user.getId());
		poemRequest.setId(Integer.parseInt(poemId));
		int response = poemService.modifyPoem(poemRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 시 삭제하기
	@DeleteMapping("/{poemId}") // postman test 완!
	public ResponseEntity<?> poemRemove(@PathVariable("poemId") int poemId){
		int response = poemService.removePoem(poemId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 시 상세보기
	@GetMapping("/{poemId}") // postman test 완!
	public ResponseEntity<Poem> poemDetail(@PathVariable("poemId") int poemId){
		Poem response = poemService.findPoemById(poemId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 특정 시집에 해당되는 시 리스트
	@GetMapping("/book/{bookId}") // postman test 완!
	public ResponseEntity<?> poemListByBookId(@PathVariable("bookId") int bookId) {
		List<Poem> response = poemService.findPoemsByBookId(bookId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 특정 유저가 쓴 시 리스트
	@GetMapping("/user/{userId}") // postman test 완!
	public ResponseEntity<?> poemListByUserId(@PathVariable("userId") int userId) {
		List<Poem> response = poemService.findPoemsByUserId(userId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 랜덤으로 나오는 시 : 전체 시 목록에서 랜덤으로 시 하나 가져오는 로직
	@GetMapping("/random") // postman test 완!
	public ResponseEntity<?> randomPoem() {
		Poem response = poemService.findRandomPoem();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 내가 구독한 작가의 모든 시 리스트를 최신순으로 가져옴.
	@GetMapping("/follow/poem")
	public ResponseEntity<?> myFollowUserPoemList() {
		List<Poem> response = new ArrayList<>();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}