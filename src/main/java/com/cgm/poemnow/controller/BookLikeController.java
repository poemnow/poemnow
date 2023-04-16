package com.cgm.poemnow.controller;

import com.cgm.poemnow.domain.BookLike;

import com.cgm.poemnow.service.BookLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/Booklike")
public class BookLikeController {

    @Autowired
    private BookLikeService booklikeService;

    //  Like!!
    @PostMapping(path = "/bookLikeAdd")
    public ResponseEntity<?> bookLikeAdd(@RequestBody BookLike likeRequest){
        int response = booklikeService.addBookLike(likeRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // 새로운 레코드 만들어야 돼서
    }

    // Unlike!!
    @DeleteMapping(path = "/bookLikeRemove")
    public ResponseEntity<?> bookLikeRemove(@RequestParam(value = "bookId") int bookId){
        int userId = 1; // 세션에서 받아 옴
        int response = booklikeService.removeBookLike(userId, bookId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 내가 좋아요한 시 보기
    @GetMapping(path = "/likeBookList")
    public ResponseEntity<List<HashMap<?, ?>>> likeBookList(){
        int userId = 1; // 세션에서 받아 옴
        List<HashMap<?, ?>> response = booklikeService.findBookLike(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}