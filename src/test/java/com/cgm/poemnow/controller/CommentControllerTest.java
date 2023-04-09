package com.cgm.poemnow.controller;

import com.cgm.poemnow.domain.Comment;
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
class CommentControllerTest {

	@Autowired
	CommentController commentController;

	@Autowired
	MockMvc mvc;

	@Test
	@DisplayName("댓글 추가 및 리스트 조회 테스트")
	void commentAddAndBookList() throws Exception{
		Comment newcomment = Comment.builder()
			.content("Test Comment")
			.userId(1)
			.poemId(1)
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
			if(2==(comment.getId())) {
				isContained = true;
				break;
			}
		}
		assertThat(isContained).isTrue();

	}

}


