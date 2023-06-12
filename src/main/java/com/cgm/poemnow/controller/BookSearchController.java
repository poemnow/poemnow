package com.cgm.poemnow.controller;

import com.cgm.poemnow.domain.Book;

import com.cgm.poemnow.service.BookSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/bookSearch")
@CrossOrigin("*")
public class BookSearchController {

    @Autowired
    private BookSearchService bookSearchService;

    @GetMapping("/bookSearchByTitle")
    public ResponseEntity<List<HashMap<String, ?>>> bookSearchByTitle(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "lastest") String sortOrder
    ){
        List<HashMap<String, ?>> response = bookSearchService.findBooksByTitle(keyword, sortOrder);
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

}