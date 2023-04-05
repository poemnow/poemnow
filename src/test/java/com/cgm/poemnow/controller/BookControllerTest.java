package com.cgm.poemnow.controller;

import com.cgm.poemnow.domain.Book;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

	@Autowired
	BookController bookController;

	@Autowired
	MockMvc mvc;

	@Test
	@DisplayName("시집 추가 테스트")
	void bookAdd() throws Exception{

		// given
		Book newbook = Book.builder()
				.title("안녕하세요! 적당히 바람이 시원해 기분이 너무 좋아요! 유후!")
				.author("박미정")
				.build();

		String jsonData = new Gson().toJson(newbook);

		mvc.perform(post("http://127.0.0.1:8080/book/bookAdd")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonData))
				.andExpect(status().isCreated()); // 아래에서 바로 조회까지 할테니 상태코드만 확인

		// 저장된 데이터를 단건으로 조회해도 되지만
		// 그냥 전체 조회 로직 테스트

		MvcResult mvcResult = mvc.perform(get("http://127.0.0.1:8080/book/bookList"))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		String jsonResult = mvcResult.getResponse().getContentAsString();

		JsonParser jsonParser = new JsonParser();
		JsonArray jsonArray = jsonParser.parse(jsonResult).getAsJsonArray();
		Gson gson = new GsonBuilder().disableHtmlEscaping().setDateFormat("yy-MM-dd hh;mm:ss").create();
		boolean isContained = false;
		for (JsonElement jsonElement : jsonArray) {
			Book book = gson.fromJson(jsonElement, Book.class);
			if(30==(book.getId())) {
				isContained = true;
				break;
			}
		}
		assertThat(isContained).isTrue();

	}
}