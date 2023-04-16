package com.cgm.poemnow.controller;

import com.cgm.poemnow.domain.PoemLike;
import com.cgm.poemnow.service.PoemLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/like")
public class PoemLikeController {
    @Autowired
    private PoemLikeService poemLikeService;

    //  Like!!
    @PostMapping(path = "/poemLikeAdd")
    public ResponseEntity<?> poemLikeAdd(@RequestBody PoemLike poemLikeRequest){

        int response = poemLikeService.addPoemLike(poemLikeRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // 새로운 레코드 만들어야 돼서

    }

    // Unlike!!
    @DeleteMapping(path = "/poemLikeRemove")
    public ResponseEntity<?> poemLikeRemove(@RequestParam(value = "poemId") int poemId){

        int userId = 1; // 세션에서 받아 옴
        int response = poemLikeService.removePoemLike(userId, poemId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // 내가 좋아요한 시 보기
    @GetMapping(path = "/likePoemList")
    public ResponseEntity<List<HashMap<?, ?>>> likePoemList(){

        int userId = 1; // 세션에서 받아 옴
        List<HashMap<?, ?>> response = poemLikeService.findPoemLike(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}