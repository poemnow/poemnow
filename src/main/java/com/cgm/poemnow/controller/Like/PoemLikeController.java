package com.cgm.poemnow.controller.Like;

import com.cgm.poemnow.domain.Like.PoemLike;
import com.cgm.poemnow.domain.User;
import com.cgm.poemnow.service.Like.PoemLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/like")
public class PoemLikeController {
    @Autowired
    private PoemLikeService poemLikeService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody User user, HttpServletRequest request){
        boolean isSuccess = poemLikeService.loginUser(user, request);
        return new ResponseEntity<>(isSuccess, HttpStatus.ACCEPTED);
    }

    //  Like!!
    @PostMapping(path = "/poemLikeAdd")
    public ResponseEntity<?> poemLikeAdd(@RequestBody PoemLike poemLikeRequest){

        int response = poemLikeService.addPoemLike(poemLikeRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // 새로운 레코드 만들어야 돼서

    }

    // Unlike!!
    @DeleteMapping(path = "/poemLikeRemove")
    public ResponseEntity<?> poemLikeRemove(@RequestParam int poemId, HttpServletRequest request){

        HttpSession session = request.getSession();
        int userId = ((User) session.getAttribute("loginUser")).getId();  // 세션에서 받아 옴
        int response = poemLikeService.removePoemLike(userId, poemId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // 내가 좋아요한 시 보기
    @GetMapping(path = "/likePoemList")
    public ResponseEntity<List<HashMap<?, ?>>> likePoemList(HttpServletRequest request){
        HttpSession session = request.getSession();
        int userId = ((User) session.getAttribute("loginUser")).getId(); // 세션에서 받아 옴
        List<HashMap<?, ?>> response = poemLikeService.findPoemLike(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}