package com.cgm.poemnow.controller;

import com.cgm.poemnow.domain.Poem;
import com.cgm.poemnow.service.PoemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PoemController {
	@Autowired
	private PoemService poemService;

	@RequestMapping("/listPoem")
	public List<Poem> getPoemList() {
		List<Poem> poemList = poemService.listPoem();
		return poemList;
	}
}
