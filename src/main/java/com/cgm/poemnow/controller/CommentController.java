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

	@PatchMapping("/commentModify")
	public ResponseEntity<?> commentModify(@RequestParam int id, @RequestParam String content) {
		Comment comment = Comment.builder().id(id).content(content).build();
		int response = commentService.modifyComment(comment);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/commentRemove/{id}")
	public ResponseEntity<?> commentRemove(@PathVariable("id") int id) {
		int response = commentService.removeComment(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
