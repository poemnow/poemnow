package com.cgm.poemnow.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cgm.poemnow.domain.Comment;
import com.cgm.poemnow.domain.User;
import com.cgm.poemnow.service.comment.CommentService;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@CrossOrigin("*")
public class CommentController {

	@Autowired
	private CommentService commentService;

	// 댓글 전체 리스트
	@GetMapping({"", "/"}) // postman test 완
	public ResponseEntity<List<Comment>> commentList() {
		List<Comment> response = commentService.findAllComments();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 댓글 등록(세션에 담긴 로그인한 유저 정보로 등록
	@PostMapping({"","/"}) // postman test 완
	public ResponseEntity<?> commentAdd(@RequestBody Comment commentRequest, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		if(user == null) {
			System.out.println("로그인 안 되어 있음 - 댓글 등록 불가");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		commentRequest.setUserId(user.getId());
		int response = commentService.addComment(commentRequest);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	// 댓글 수정
	@PutMapping("/{commentId}") // postman test 완!
	public ResponseEntity<?> commentModify(@RequestBody Comment commentRequest, @PathVariable String commentId, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		if(user == null){
			System.out.println("로그인 안 되어 있음 - 댓글 수정 불가");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		commentRequest.setUserId(user.getId());
		commentRequest.setId(Integer.parseInt(commentId));
		int response = commentService.modifyComment(commentRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 댓글 삭제처리
	@DeleteMapping("/deleted/{commentId}") // postman test 완!
	public ResponseEntity<?> commentRemove(@PathVariable String commentId) {
		int response = commentService.removeComment(Integer.parseInt(commentId));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 유저 아이디로 댓글 리스트
	@GetMapping("/user/{id}") // postman test 완!
	public ResponseEntity<List<Comment>>  myCommentListByUserId(@PathVariable(value="id") int userId) {
		List<Comment> response = commentService.findCommentsByUserId(userId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 시 아이디로 댓글 리스트
	@GetMapping("/poem/{poemId}") // postman test 완!
	public ResponseEntity<List<Comment>> myCommentListByPoemId(@PathVariable(value="poemId") int poemId){
		List<Comment> response = commentService.findCommentsOfThePoem(poemId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// 시별 댓글 개수
	@GetMapping("/poem/count/{poemId}") // postman test 완!
	public ResponseEntity<Integer> myCommentCountByPoemId(@PathVariable(value="poemId") int poemId){
		int response = commentService.findCommentCount(poemId);
		return new ResponseEntity<Integer>(response, HttpStatus.OK);
	}

	// 탈퇴 시 댓글 비공개 처리를 위한 유저 당 댓글 전체 삭제 처리
	@PutMapping("/deleted/user/{userId}") // todo 로직 cascade
	public ResponseEntity<?> userCommentRemove(@PathVariable String userId) {
		int response = commentService.removeCommentsByUserId(Integer.parseInt(userId));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
