package com.cgm.poemnow.controller;

import java.util.List;

import com.cgm.poemnow.domain.Poem;
import com.cgm.poemnow.service.poem.PoemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/poem")
public class PoemController {
	@Autowired
	private PoemService poemService;

	@RequestMapping("/listPoem")
	public List<Poem> getPoemList() {
		List<Poem> poemList = poemService.listPoem();
		return poemList;
	}
}
