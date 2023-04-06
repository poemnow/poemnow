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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
	@DisplayName("시집 추가 및 리스트 조회 테스트")
	void bookAddAndBookList() throws Exception{

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
			if(34==(book.getId())) {
				isContained = true;
				break;
			}
		}
		assertThat(isContained).isTrue();

	}

	@Test
	@DisplayName("시집 id로 조회 테스트")
	void bookDetailTest() throws Exception {

		// given
		int id = 3;

		MvcResult mvcResult = mvc.perform(get("/book/bookDetail/" + id))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		String response = mvcResult.getResponse().getContentAsString();

		Gson gson = new Gson();
		Book selectedBook = gson.fromJson(response, Book.class);

		// 조회된 이름과 비교해줍니다.
		assertThat(selectedBook.getId()).isEqualTo(3);
	}

	@Test
	@DisplayName("시집 수정 및 조회 확인 테스트")
	void bookUpdateTest() throws Exception {

		// 수정 이전 정보 출력 + 수정 + 수정 후 정보 출력

		// 수정 이전 정보 출력
		// given
		int id = 3;

		System.out.println(":::::::::: before update :::::::::");

		mvc.perform(get("/book/bookDetail/" + id))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		// given
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		String newTitle = "수정된 시집 제목";
		params.add("title", newTitle);
		params.add("id", String.valueOf(3));
		mvc.perform(patch("/book/bookModify")
						.params(params))
				.andExpect(status().isOk());

		mvc.perform(get("/book/bookDetail/" + id))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

	}

	@Test
	@DisplayName("시집 삭제 및 데이터 개수 변화 확인 테스트")
	void bookRemoveTest() throws Exception {

		// 전체조회하여 첫번째 데이터를 꺼내옵니다.
		MvcResult mvcResult1 = mvc.perform(get("/book/bookList"))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		String jsonResult = mvcResult1.getResponse().getContentAsString();

		JsonParser jsonParser = new JsonParser();
		JsonArray jsonArray = jsonParser.parse(jsonResult).getAsJsonArray();

		int id = jsonArray.get(0).getAsJsonObject().get("id").getAsInt();
		int size = jsonArray.size();

		// 첫번째 데이터를 삭제합니다.
		mvc.perform(delete("/book/bookRemove/"+id))
				.andExpect(status().isOk());

		MvcResult mvcResult2 = mvc.perform(get("/book/bookList"))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		String jsonResult2 = mvcResult2.getResponse().getContentAsString();

		// 전체 데이터갯수가 한개 줄었나 확인해줍니다.
		assertThat(jsonParser.parse(jsonResult2).getAsJsonArray().size()).isEqualTo(size-1);

	}

}