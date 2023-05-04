package com.cgm.poemnow.controller.Like;

import com.cgm.poemnow.domain.Like.PoemLike;
import com.cgm.poemnow.domain.Poem;
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
@RequestMapping("/poem-like")
public class PoemLikeController {
    @Autowired
    private PoemLikeService poemLikeService;

    //  Like!!
    @PostMapping(path = "/poem-like")
    public ResponseEntity<?> poemLikeAdd(@RequestBody PoemLike poemLikeRequest, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        poemLikeRequest.setUserId(user.getId());
        int response = poemLikeService.addPoemLike(poemLikeRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // 새로운 레코드 만들어야 돼서

    }

    // Unlike!!
    @DeleteMapping(path = "/poem-like/{poemId}")
    public ResponseEntity<?> poemLikeRemove(HttpServletRequest request, @PathVariable String poemId){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");  // 세션에서 받아 옴
        int response = poemLikeService.removePoemLike(user.getId(), Integer.parseInt(poemId));
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // 내가 좋아요한 시 보기
    @GetMapping(path = {"/poem-like/poem/{id}", "/poem-like/poem"})
    public ResponseEntity<List<HashMap<?, ?>>> likePoemList(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser"); // 세션에서 받아 옴
        List<HashMap<?, ?>> response = poemLikeService.findPoemLike(user.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 시 별 좋아요 수 구하기
    @GetMapping(path = {"/poem-like/poem/count/{poemId}", "/poem-like/poem/count"})
    public ResponseEntity<Integer> likePoemPoemCount(@PathVariable(required = false) int poemId) {
        int response = poemLikeService.findPoemLikePoemCount(poemId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 내가 좋아요한 시 수 구하기
    @GetMapping(path = {"/poem-like/user/count/{id}", "/poem-like/user/count"})
    public ResponseEntity<Integer> likePoemUserCnt(HttpServletRequest request, @PathVariable(required = false) String id) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");

        if(id != null ){
            user.setId(Integer.parseInt(id));
        }
        int response = poemLikeService.findPoemLikeUserCount(user.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}