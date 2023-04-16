package com.cgm.poemnow.controller;

import java.util.List;

import com.cgm.poemnow.domain.Comment;
import com.cgm.poemnow.service.comment.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping("/commentAdd")
	public ResponseEntity<?> commentAdd(@RequestBody Comment commentRequest) {
		int response = commentService.addComment(commentRequest);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/commentList")
	public ResponseEntity<List<Comment>> commentList() {
		List<Comment> response = commentService.findAllComments();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/commentModify")
	public ResponseEntity<?> commentModify(@RequestBody Comment commentRequest) {
		int response = commentService.modifyComment(commentRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/commentRemove")
	public ResponseEntity<?> commentRemove(@RequestBody Comment commentRequest) {
		int response = commentService.removeComment(commentRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
