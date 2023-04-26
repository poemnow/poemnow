package com.cgm.poemnow.controller.Like;

import com.cgm.poemnow.domain.Like.CommentLike;
import com.cgm.poemnow.domain.User;
import com.cgm.poemnow.service.Like.CommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/commentlike")
public class CommentLikeController {

    @Autowired
    private CommentLikeService CommentlikeService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody User user, HttpServletRequest request){
        boolean isSuccess = CommentlikeService.loginUser(user, request);
        return new ResponseEntity<>(isSuccess, HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/CommentLikeAdd")
    public ResponseEntity<?> CommentLikeAdd(@RequestBody CommentLike likeRequest){
        int response = CommentlikeService.addCommentLike(likeRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/CommentLikeRemove")
    public ResponseEntity<?> poemLikeRemove(@RequestParam(value = "commentId") int commentId, HttpServletRequest request){
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");
        int response = CommentlikeService.removeCommentLike(userId, commentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/likeCommentList")
    public ResponseEntity<List<HashMap<?, ?>>> likeCommentList(HttpServletRequest request){
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId");
        List<HashMap<?, ?>> response = CommentlikeService.findCommentLike(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}