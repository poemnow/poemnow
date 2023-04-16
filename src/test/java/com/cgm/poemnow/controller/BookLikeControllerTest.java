package com.cgm.poemnow.controller;

import com.cgm.poemnow.domain.BookLike;
import com.cgm.poemnow.service.BookLikeService;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookLikeControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    BookLikeService booklikeService;

    @Autowired
    BookLikeController booklikeController;

    @Test
    @DisplayName("시집 좋아요 추가 및 리스트 조회 테스트")
    void poemLikeAddAndpoemLikeList() throws Exception {
        BookLike newLike = BookLike.builder()
                .userId(1)
                .bookId(4)
                .build();
        String jsonData = new Gson().toJson(newLike);

        mvc.perform(post("http://127.0.0.1:8080/Booklike/bookLikeAdd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andExpect(status().isCreated());

        String userId="1";
        MvcResult mvcResult = mvc.perform(get("http://127.0.0.1:8080/Booklike/likeBookList")
                        .contentType(MediaType.APPLICATION_JSON) //타입설정
                        .content(userId))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String jsonResult = mvcResult.getResponse().getContentAsString();

        JsonParser jsonParser = new JsonParser();

        JsonArray jsonArray = jsonParser.parse(jsonResult).getAsJsonArray();
        Gson gson = new GsonBuilder().disableHtmlEscaping().setDateFormat("yy-MM-dd hh;mm:ss").create();
        boolean isContained = false;
        //배열에 있는거를 하나씩 jsonElement에 넣어
        //그걸 객체로 변환시켜서 넣어
        for (JsonElement jsonElement : jsonArray) {
            BookLike likes = gson.fromJson(jsonElement, BookLike.class);
            if (4 == (likes.getBookId())) {
                isContained = true;
                break;
            }
        }
        assertThat(isContained).isTrue();
    }

    @Test
    @DisplayName("시집 좋아요 삭제 및 데이터 개수 변화 확인 테스트")
    void poemLikeRemoveTest() throws Exception {

        // 전체조회하여 첫번째 데이터를 꺼내옵니다.
        MvcResult mvcResult1 = mvc.perform(get("/Booklike/likeBookList"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String jsonResult = mvcResult1.getResponse().getContentAsString();

        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = jsonParser.parse(jsonResult).getAsJsonArray();

        int id = jsonArray.get(0).getAsJsonObject().get("bookId").getAsInt();
        int size = jsonArray.size();

        // 첫번째 데이터를 삭제합니다.
        mvc.perform(delete("/Booklike/bookLikeRemove?bookId="+id))
                .andExpect(status().isOk());

        MvcResult mvcResult2 = mvc.perform(get("/Booklike/likeBookList"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String jsonResult2 = mvcResult2.getResponse().getContentAsString();

        // 전체 데이터갯수가 한개 줄었나 확인해줍니다.
        assertThat(jsonParser.parse(jsonResult2).getAsJsonArray().size()).isEqualTo(size-1);

    }


}