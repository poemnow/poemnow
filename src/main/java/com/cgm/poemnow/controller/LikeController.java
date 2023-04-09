package com.cgm.poemnow.controller;

import com.cgm.poemnow.domain.Like;
import com.cgm.poemnow.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/like")
public class LikeController {
    @Autowired
    private LikeService likeService;

    //  Like!!
    @PostMapping(path = "/likePoem")
    public ResponseEntity<?> like(@RequestBody Like likeRequest){
        int response = likeService.likePoem(likeRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // 새로운 레코드 만들어야 돼서
    }

    // Unlike!!
    @DeleteMapping(path = "/unlikePoem")
    public ResponseEntity<?> unlike(@RequestParam(value = "poemId") int poemId){
        int userId = 1; // 세션에서 받아 옴
        int response = likeService.unLikePoem(userId, poemId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 내가 좋아요한 시 보기
    @GetMapping(path = "/likePoemList")
    public ResponseEntity<List<HashMap<?, ?>>> likePoemList(){
        int userId = 1; // 세션에서 받아 옴
        List<HashMap<?, ?>> response = likeService.likePoemList(userId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}