package com.cgm.poemnow.controller;

import java.util.ArrayList;
import java.util.List;

import com.cgm.poemnow.domain.book.Book;
import com.cgm.poemnow.domain.BookPoem;
import com.cgm.poemnow.domain.book.BookRequest;
import com.cgm.poemnow.domain.Poem;
import com.cgm.poemnow.service.book.BookService;
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
import org.springframework.transaction.annotation.Transactional;
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
	BookService bookService;

	@Autowired
	MockMvc mvc;

	@Test
	@Transactional
	@DisplayName("시집 추가 및 리스트 조회 테스트")
	void bookAddAndBookList() throws Exception{

		// given
		Book newbook = Book.builder()
				.title("안녕하세요! 적당히 바람이 시원해 기분이 너무 좋아요! 유후!")
				.userId("박미정")
				.build();

		BookPoem newBookPoem1 = BookPoem.builder()
				.poemId(1)
				.poemOrder(2)
				.build();
		BookPoem newBookPoem2 = BookPoem.builder()
				.poemId(2)
				.poemOrder(3)
				.build();
		BookPoem newBookPoem3 = BookPoem.builder()
				.poemId(3)
				.poemOrder(4)
				.build();
		BookPoem newBookPoem4 = BookPoem.builder()
				.poemId(13)
				.poemOrder(5)
				.build();

		List<BookPoem> bookPoemList = new ArrayList<>();

		bookPoemList.add(newBookPoem1);
		bookPoemList.add(newBookPoem2);
		bookPoemList.add(newBookPoem3);
		bookPoemList.add(newBookPoem4);

		BookRequest bookRequest = new BookRequest();
		bookRequest.setBookPoemList(bookPoemList);
		bookRequest.setBook(newbook);

		String jsonDataBook = new Gson().toJson(bookRequest);

		mvc.perform(post("http://127.0.0.1:8080/book/bookAdd")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonDataBook))
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
		Gson gson = new Gson();
		boolean isContained = false;
		for (JsonElement jsonElement : jsonArray) {
			int id = gson.fromJson(jsonElement, Book.class).getId();
			if(63==(id)) {
				isContained = true;
				break;
			}
		}
		assertThat(isContained).isTrue();

	}

	@Test
	@Transactional
	@DisplayName("시집 id로 조회 테스트")
	void bookDetailTest() throws Exception {

		// given
		int id = 63;

		MvcResult mvcResult = mvc.perform(get("/book/bookDetail/" + id))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		String response = mvcResult.getResponse().getContentAsString();

		Gson gson = new Gson();
		Book selectedBook = gson.fromJson(response, Book.class);

		// 조회된 이름과 비교해줍니다.
		assertThat(selectedBook.getId()).isEqualTo(63);

		MvcResult mvcResult1 = mvc.perform(get("/book/poemListByBookId/"+id))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		String jsonResult = mvcResult1.getResponse().getContentAsString();

		gson = new GsonBuilder().disableHtmlEscaping().create();
		JsonParser jsonParser = new JsonParser();
		JsonArray jsonArray = jsonParser.parse(jsonResult).getAsJsonArray();

		for (JsonElement je: jsonArray){
			Poem p = gson.fromJson(je, Poem.class);
			System.out.println(p);
		}


	}

	@Test
	@DisplayName("시집 수정 및 조회 확인 테스트")
	void bookUpdateTest() throws Exception {

		// 수정 이전 정보 출력 + 수정 + 수정 후 정보 출력

		// 수정 이전 정보 출력
		// given
		int id = 64;

		System.out.println(":::::::::: before update :::::::::");

		mvc.perform(get("/book/bookDetail/" + id))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		// given
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		String newTitle = "수정된 시집 제목";
		params.add("title", newTitle);
		params.add("id", String.valueOf(id));
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