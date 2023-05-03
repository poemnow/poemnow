package com.cgm.poemnow.controller.Like;

import com.cgm.poemnow.domain.Like.BookLike;

import com.cgm.poemnow.domain.User;
import com.cgm.poemnow.service.Like.BookLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/Booklike")
public class BookLikeController {

    @Autowired
    private BookLikeService booklikeService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody User user, HttpServletRequest request){
        boolean isSuccess = booklikeService.loginUser(user, request);
        return new ResponseEntity<>(isSuccess, HttpStatus.ACCEPTED);
    }

    //  Like!!
    @PostMapping(path = "/bookLikeAdd")
    public ResponseEntity<?> bookLikeAdd(@RequestBody BookLike likeRequest){
        int response = booklikeService.addBookLike(likeRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // 새로운 레코드 만들어야 돼서
    }

    // Unlike!!
    @DeleteMapping(path = "/bookLikeRemove")
    public ResponseEntity<?> bookLikeRemove(@RequestParam(value = "bookId") int bookId, HttpServletRequest request){
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId"); // 세션에서 받아 옴
        int response = booklikeService.removeBookLike(userId, bookId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 내가 좋아요한 시 보기
    @GetMapping(path = "/likeBookList")
    public ResponseEntity<List<HashMap<?, ?>>> likeBookList(HttpServletRequest request){
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userId"); // 세션에서 받아 옴
        List<HashMap<?, ?>> response = booklikeService.findBookLike(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}