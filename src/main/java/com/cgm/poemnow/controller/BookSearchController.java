package com.cgm.poemnow.controller;

import com.cgm.poemnow.domain.Book;

import com.cgm.poemnow.service.BookSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookSearch")
public class BookSearchController {
    @Autowired
    private BookSearchService bookSearchService;

    @GetMapping("/bookSearchByTitle")
    public ResponseEntity<?> bookSearchByTitle(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "lastest") String sortOrder
    ){
        List<Book> response = bookSearchService.findBooksByTitle(keyword, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/bookSearchByPoem")
    public ResponseEntity<?> bookSearchByPoem(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "lastest") String sortOrder
    ){
        List<Book> response = bookSearchService.findBooksByPoem(keyword, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/bookSearchByAuthor")
    public ResponseEntity<?> bookSearchByAuthor(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "lastest") String sortOrder
    ){
        List<Book> response = bookSearchService.findBooksByAuthor(keyword, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    시집 검색에 태그 검색이 필요할까?
//    @GetMapping("/bookSearchByTag/{keyword}")
//    // 사용자가 입력한 스트링을 포함한 bookList를 요청합니다.
//    public ResponseEntity<?> bookSearchByTag(@RequestParam String keyword){
//        List<Book> response = bookSearchService.findBooksByTag(keyword);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }
}