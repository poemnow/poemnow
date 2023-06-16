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
@RequestMapping("/comment-like")
@CrossOrigin("*")
public class CommentLikeController {

    @Autowired
    private CommentLikeService commentlikeService;

    // 댓글 좋아요 추가
    @PostMapping(path = "")
    public ResponseEntity<?> CommentLikeAdd(@RequestBody CommentLike likeRequest, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        likeRequest.setUserId(1);
        int response = commentlikeService.addCommentLike(likeRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 댓글 좋아요 취소
    @DeleteMapping(path = "/{commentId}")
    public ResponseEntity<?> poemLikeRemove(@PathVariable String commentId, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        int response = commentlikeService.removeCommentLike(1, Integer.parseInt(commentId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 내가 좋아요한 댓글 보기
    @GetMapping(path = {"/comment", "/comment/{id}"})
    public ResponseEntity<List<HashMap<?, ?>>> likeCommentList(HttpServletRequest request){
//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("loginUser");
        List<HashMap<?, ?>> response = commentlikeService.findCommentLike(1);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //  좋아요 한 댓글 수 구하기
    @GetMapping(path = {"comment/count/{id}", "comment/count"})
    public ResponseEntity<Integer> likeCommentCnt(HttpServletRequest request, @PathVariable(required = false) String id) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");

        if(id != null ){
            user.setId(Integer.parseInt(id));
        }
        int response = commentlikeService.findCommentLikeCnt(user.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //댓글 별 좋아요 수 구하기
    @GetMapping(path = {"/comment/user/count/{commentId}", "/comment/user/count"})
    public ResponseEntity<Integer> likeCommentUserCnt(@PathVariable(required = false) int commentId) {
        int response = commentlikeService.findCommentLikeUserCount(commentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}