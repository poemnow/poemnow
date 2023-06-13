package com.cgm.poemnow.controller;

import com.cgm.poemnow.domain.Poem;

import com.cgm.poemnow.domain.User;
import com.cgm.poemnow.domain.response.PoemSearchResponse;
import com.cgm.poemnow.service.PoemSearchService;
import com.cgm.poemnow.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/poemSearch")
public class PoemSearchController {
    @Autowired
    private PoemSearchService PoemSearchService;

    @Autowired
    private UserService userService;

    @GetMapping("/poemSearchByTitle")
    public ResponseEntity<?> poemSearchByTitle(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "lastest") String sortOrder
    ){
        List<Poem> response = PoemSearchService.findPoemsByTitle(keyword, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/poemSearchByContent")
    public ResponseEntity<?> poemSearchByContent(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "lastest") String sortOrder
    ){
        List<Poem> response = PoemSearchService.findPoemsByContent(keyword, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 제목과 내용으로 시 검색하기
    // json 형식 반환: poem(Poem), writer(User-작가), commentCnt(int-시에 달린 댓글 개수)
    @GetMapping("/poemSearchByTitleAndContent")
    public ResponseEntity<?> poemSearchByTitleAndContent(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "lastest") String sortOrder
    ) throws UnsupportedEncodingException {
        keyword = URLDecoder.decode(keyword, "UTF-8");
//        keyword = new String(keyword.getBytes("8859_1"), "UTF-8");
        System.out.println(keyword + "keyword//////");
        List<PoemSearchResponse> response = new ArrayList<>();
        List<Poem> tempPoemList = PoemSearchService.findPoemsByTitleAndContent(keyword, sortOrder);
        for (Poem p:tempPoemList
             ) {
            PoemSearchResponse temp = new PoemSearchResponse();
            temp.setPoem(p);
            System.out.println("poem: " + p.toString());
            User user = userService.findUserById(p.getUserId());
            temp.setWriter(user);
            int commentCnt = PoemSearchService.findCommentCntByPoemId(p.getId());
            temp.setCommentCnt(commentCnt);
            response.add(temp);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/poemSearchByAuthor")
    public ResponseEntity<?> poemSearchByAuthor(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "lastest") String sortOrder
    ){
        List<Poem> response = PoemSearchService.findPoemsByAuthor(keyword, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/poemSearchByTag")
    public ResponseEntity<?> poemSearchByTag(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "lastest") String sortOrder
    ){
        List<Poem> response = PoemSearchService.findPoemsByTag(keyword, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}