package com.cgm.poemnow;

import com.cgm.poemnow.service.PoemServiceImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PoemnowApplicationTests {

	@Autowired
	PoemServiceImpl poemService;

	@Test
	void contextLoads() {
	}

	@Test
	void getPoemList() {
		String result = poemService.listPoem().toString();
		System.out.println("result : " + result);
	}

}
