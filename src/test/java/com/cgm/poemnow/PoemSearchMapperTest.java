package com.cgm.poemnow;

import com.cgm.poemnow.controller.PoemSearchController;

import com.cgm.poemnow.domain.Poem;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PoemSearchMapperTest {
    @Autowired
    PoemSearchController poemSearchController;

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("시 제목으로 조회 테스트 / 등록순")
    void selectPoemsByTitleTest() throws Exception {
        // given
        String title = "나팔꽃";

        MvcResult mvcResult = mvc.perform(get("http://127.0.0.1:8080/poemSearch/poemSearchByTitle?keyword=" + title)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Gson gson = new Gson();

        Type listType = new TypeToken<List<Object>>() {
        }.getType();
        List<Object> resultList = gson.fromJson(response, listType);
        List<Poem> poemList = new ArrayList<>();
        for (Object obj : resultList) {
            Poem poem = gson.fromJson(gson.toJson(obj), Poem.class);
            poemList.add(poem);
        }

        assertThat(poemList.get(0).getId()).isEqualTo(6);

    }

    @Test
    @DisplayName("시 내용으로 시 조회 테스트 / 등록순")
    void selectPoemsByContentTest() throws Exception {
        // given
        String content = "빨간콩";

        MvcResult mvcResult = mvc.perform(get("http://127.0.0.1:8080/poemSearch/poemSearchByContent?keyword=" + content)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Gson gson = new Gson();

        Type listType = new TypeToken<List<Object>>() {
        }.getType();
        List<Object> resultList = gson.fromJson(response, listType);
        List<Poem> poemList = new ArrayList<>();
        for (Object obj : resultList) {
            Poem poem = gson.fromJson(gson.toJson(obj), Poem.class);
            poemList.add(poem);
        }

        assertThat(poemList.get(0).getId()).isEqualTo(2);

    }

    @Test
    @DisplayName("시 제목 또는 내용으로 시 조회 테스트 / 등록순")
    void selectPoemsByTitleAndContentTest() throws Exception {
        // given
        String keyword = "은";

        MvcResult mvcResult = mvc.perform(get("http://127.0.0.1:8080/poemSearch/poemSearchByTitleAndContent?keyword=" + keyword)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Gson gson = new Gson();

        Type listType = new TypeToken<List<Object>>() {
        }.getType();
        List<Object> resultList = gson.fromJson(response, listType);
        List<Poem> poemList = new ArrayList<>();
        for (Object obj : resultList) {
            Poem poem = gson.fromJson(gson.toJson(obj), Poem.class);
            poemList.add(poem);
        }

        assertThat(poemList.size()).isEqualTo(5);

    }

    @Test
    @DisplayName("시인 이름으로 시 조회 테스트 / 등록순")
    void selectPoemsByAuthorTest() throws Exception {
        // given
        String author = "콩";

        MvcResult mvcResult = mvc.perform(get("http://127.0.0.1:8080/poemSearch/poemSearchByAuthor?keyword=" + author)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Gson gson = new Gson();

        Type listType = new TypeToken<List<Object>>() {
        }.getType();
        List<Object> resultList = gson.fromJson(response, listType);
        List<Poem> poemList = new ArrayList<>();
        for (Object obj : resultList) {
            Poem poem = gson.fromJson(gson.toJson(obj), Poem.class);
            poemList.add(poem);
        }

        assertThat(poemList.size()).isEqualTo(3);

    }

    @Test
    @DisplayName("태그로 시 조회 테스트 / 등록순")
    void selectPoemsByTagTest() throws Exception {
        // given
        String tag = "꽃";

        MvcResult mvcResult = mvc.perform(get("http://127.0.0.1:8080/poemSearch/poemSearchByTag?keyword=" + tag)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Gson gson = new Gson();

        Type listType = new TypeToken<List<Object>>() {
        }.getType();
        List<Object> resultList = gson.fromJson(response, listType);
        List<Poem> poemList = new ArrayList<>();
        for (Object obj : resultList) {
            Poem poem = gson.fromJson(gson.toJson(obj), Poem.class);
            poemList.add(poem);
        }

        assertThat(poemList.size()).isEqualTo(3);

    }


}
