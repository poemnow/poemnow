package com.cgm.poemnow.controller;

import com.cgm.poemnow.domain.CommentLike;
import com.cgm.poemnow.service.CommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/commentlike")
public class CommentLikeController {

    @Autowired
    private CommentLikeService CommentlikeService;

    @PostMapping(path = "/CommentLikeAdd")
    public ResponseEntity<?> CommentLikeAdd(@RequestBody CommentLike likeRequest){
        int response = CommentlikeService.addCommentLike(likeRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/CommentLikeRemove")
    public ResponseEntity<?> poemLikeRemove(@RequestParam(value = "commentId") int commentId){
        int userId = 1;
        int response = CommentlikeService.removeCommentLike(userId, commentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/likeCommentList")
    public ResponseEntity<List<HashMap<?, ?>>> likeCommentList(){
        int userId = 1;
        List<HashMap<?, ?>> response = CommentlikeService.findCommentLike(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}