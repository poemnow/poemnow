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
@RequestMapping("/book-like")
public class BookLikeController {

    @Autowired
    private BookLikeService booklikeService;

    //  Like!!
    @PostMapping(path = "")
    public ResponseEntity<?> bookLikeAdd(@RequestBody BookLike likeRequest, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        likeRequest.setUserId(user.getId());
//        if(user.getId() == likeRequest.getUserId()){
//            // 좋아요 하는 유저 아이디(주요키)랑 세션에 로그인된 유저 아이디(주요키)랑 같을 때
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
        int response = booklikeService.addBookLike(likeRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // 새로운 레코드 만들어야 돼서
    }

    // Unlike!!
    @DeleteMapping(path = "/{bookId}")
    public ResponseEntity<?> bookLikeRemove(@PathVariable String bookId, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        int response = booklikeService.removeBookLike(user.getId(), Integer.parseInt(bookId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 내가 좋아요한 시집 보기
    @GetMapping(path = {"/book", "/book/{id}"})
    public ResponseEntity<List<HashMap<?, ?>>> likeBookList(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        List<HashMap<?, ?>> response = booklikeService.findBookLike(user.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 시집 별 좋아요 수 구하기
    @GetMapping(path = {"/book/count/{bookId}", "/book/count/{bookId}"})
    public ResponseEntity<Integer> likeBookBookCnt(@PathVariable(required = false) int bookId) {
        int response = booklikeService.findBookLikeBookCount(bookId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    // 내가 좋아요한 시집 수 구하기
    @GetMapping(path = {"/user/count/{id}", "/user/count"})
    public ResponseEntity<Integer> likeBookUserCnt(HttpServletRequest request, @PathVariable(required = false) String id) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");

        if(id != null ){
            user.setId(Integer.parseInt(id));
        }
        int response = booklikeService.findBookLikeUserCount(user.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}