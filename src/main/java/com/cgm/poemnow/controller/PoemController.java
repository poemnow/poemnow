package com.cgm.poemnow.controller;

import com.cgm.poemnow.domain.Poem;
import com.cgm.poemnow.service.PoemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/poem")
public class PoemController {

	@Autowired
	private PoemService poemService;

	@PostMapping("/poemAdd")
	public ResponseEntity<?> poemAdd(@RequestBody Poem poemRequest){
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