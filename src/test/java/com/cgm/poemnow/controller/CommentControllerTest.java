package com.cgm.poemnow.controller;
import com.cgm.poemnow.domain.Comment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

	@Autowired
	CommentController commentController;

	@Autowired
	MockMvc mvc;

	@Test
	@DisplayName("댓글 추가 및 리스트 조회 테스트")
	void commentAddAndCommentList() throws Exception{
		Comment newcomment = Comment.builder()
			.content("새로운 댓글 추가입니다.")
			.userId(1)
			.poemId(2)
			.build();

		String jsonData = new Gson().toJson(newcomment);

		mvc.perform(post("http://127.0.0.1:8080/comment/commentAdd")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonData))
				.andExpect(status().isCreated());

		MvcResult mvcResult =mvc.perform(get("http://127.0.0.1:8080/comment/commentList"))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		String jsonResult = mvcResult.getResponse().getContentAsString();

		JsonParser jsonParser = new JsonParser();
		JsonArray jsonArray = jsonParser.parse(jsonResult).getAsJsonArray();
		Gson gson = new GsonBuilder().disableHtmlEscaping().setDateFormat("yy-MM-dd hh;mm:ss").create();
		boolean isContained = false;
		for (JsonElement jsonElement : jsonArray) {
			Comment comment = gson.fromJson(jsonElement, Comment.class);
			if(20==(comment.getId())) {
				isContained = true;
				break;
			}
		}
		assertThat(isContained).isTrue();

	}

	@Test
	@DisplayName("댓글 수정 및 조회 확인 테스트")
	public void commentModifyTest() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();

		int id = 8;

		//리스트를 전체 가져와서, 리스트에서 포문 돌려서 아이디 같은애를 찾아서 값이 바뀐지본다.
		mvc.perform(get("/comment/commentList"))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.[?(@.id == 8)]").exists())
				.andReturn();

		System.out.println("--------------------수정 전------------------------- ");

		String newContent = "완전완전완전완전 새로운 댓글 내용";

		Comment newComment = new Comment();
		newComment.setId(id);
		newComment.setContent(newContent);

		// 수정 요청 보냄
		mvc.perform(put("/comment/commentModify/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(newComment)))
				.andExpect(status().isOk());

		System.out.println("--------------------수정 후------------------------- ");

		// 수정한 댓글이 맞는지 확인
		mvc.perform(get("/comment/commentList"))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.[?(@.id == 8)].content").value(newContent));

	}

	@Test
	@DisplayName("댓글 삭제 및 조회 확인 테스트")
	public void commentRemoveTest() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();

		int id = 10;

		// 댓글 삭제 전 리스트에서 확인
		mvc.perform(get("/comment/commentList"))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.[?(@.id == 10)]").exists())
				.andReturn();

		Comment newComment = new Comment();
		newComment.setId(id);
		newComment.setDeleted(true);

		// 댓글 삭제 요청
		mvc.perform(put("/comment/commentRemove")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(newComment)))
				.andExpect(status().isOk());

		// 댓글 삭제 후 리스트에서 확인
		mvc.perform(get("/comment/commentList"))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.[?(@.id == 10)].deleted").value(true));

	}

	@Test
	@DisplayName("user_id로 삭제 테스트")
	public void commentRemoveByUserIdTest() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();

		int userId = 1;

		// 댓글 삭제 전 리스트에서 확인
		mvc.perform(put("/comment/userCommentRemove")
						.contentType(MediaType.APPLICATION_JSON)
						.content(String.valueOf(userId)))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();


		// 댓글 삭제 후 리스트에서 확인
		mvc.perform(get("/comment/commentList"))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

	}

	@Test
	@DisplayName("poem_id로 조회 테스트")
	public void myCommentListTest() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();

//		int poemId = 2;


		// 댓글 삭제 전 리스트에서 확인
		mvc.perform(get("http://localhost:8080/comment/myComments?peomId=2")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
	}

}


