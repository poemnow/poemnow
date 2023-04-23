package com.cgm.poemnow.controller;

import com.cgm.poemnow.domain.User;

import com.cgm.poemnow.service.UserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userSearch")
public class UserSearchController {
    @Autowired
    private UserSearchService userSearchService;

    @GetMapping("/userSearchByNickname")
    public ResponseEntity<?> poemSearchByTitle(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "lastest") String sortOrder
    ){
        List<User> response = userSearchService.findUsersByNickname(keyword, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}