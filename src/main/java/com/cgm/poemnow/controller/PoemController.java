package com.cgm.poemnow.controller;

import com.cgm.poemnow.domain.Poem;
import com.cgm.poemnow.domain.User;
import com.cgm.poemnow.service.poem.PoemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/poem")
public class PoemController {

	@Autowired
	private PoemService poemService;

	// 시 등록(세션에 담긴 로그인한 유저 정보로 등록)
	@PostMapping("/poemAdd")
	public ResponseEntity<?> poemAdd(@RequestBody Poem poemRequest, HttpServletRequest request){
		HttpSession session = request.getSession();
		if (session.getAttribute("loginUser")==null){
			System.out.println("로그인 안 되어 있음 - 시 등록 불가");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		poemRequest.setUserId(((User)session.getAttribute("loginUser")).getId());
		int response = poemService.addPoem(poemRequest);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping(path = "/poemList")
	public ResponseEntity<List<Poem>> poemList() {
		List<Poem> response = poemService.findAllPoems();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/poemDetail/{id}")
	public ResponseEntity<Poem> poemDetail(@PathVariable("id") int id){
		Poem response = poemService.findPoemById(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/poemModify")
	public ResponseEntity<?> poemModify(@RequestBody Poem poemRequest){
		int response = poemService.modifyPoem(poemRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/poemRemove/{id}")
	public ResponseEntity<?> poemRemove(@PathVariable("id") int id){
		int response = poemService.removePoem(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}