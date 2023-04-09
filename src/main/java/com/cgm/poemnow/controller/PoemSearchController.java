package com.cgm.poemnow.controller;

import com.cgm.poemnow.domain.Poem;

import com.cgm.poemnow.service.PoemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/poemSearch")
public class PoemSearchController {
    @Autowired
    private PoemSearchService PoemSearchService;

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

    @GetMapping("/poemSearchByTitleAndContent")
    public ResponseEntity<?> poemSearchByTitleAndContent(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "lastest") String sortOrder
    ){
        List<Poem> response = PoemSearchService.findPoemsByTitleAndContent(keyword, sortOrder);
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