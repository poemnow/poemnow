package com.cgm.poemnow;

import com.cgm.poemnow.controller.BookSearchController;

import com.cgm.poemnow.domain.book.Book;

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
public class BookSearchMapperTest {
    @Autowired
    BookSearchController bookSearchController;

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("시 제목으로 조회 테스트 / 등록순")
    void selectBooksByTitleTest() throws Exception {
        // given
        String title = "sogreat";

        MvcResult mvcResult = mvc.perform(get("http://127.0.0.1:8080/bookSearch/bookSearchByTitle?keyword=" + title)
                        .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Gson gson = new Gson();

        Type listType = new TypeToken<List<Object>>(){}.getType();
        List<Object> resultList = gson.fromJson(response, listType);
        List<Book> bookList = new ArrayList<>();
        for (Object obj : resultList) {
            Book book = gson.fromJson(gson.toJson(obj), Book.class);
            bookList.add(book);
        }

        assertThat(bookList.get(0).getTitle()).isEqualTo("sogreat");

    }

    @Test
    @DisplayName("특정 시를 포함한 시집 조회 테스트 / 등록순")
    void selectBooksByPoemTest() throws Exception {
        // given
        String title = "꽃";

        MvcResult mvcResult = mvc.perform(get("http://127.0.0.1:8080/bookSearch/bookSearchByPoem?keyword=" + title)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Gson gson = new Gson();

        Type listType = new TypeToken<List<Object>>(){}.getType();
        List<Object> resultList = gson.fromJson(response, listType);
        List<Book> bookList = new ArrayList<>();
        for (Object obj : resultList) {
            Book book = gson.fromJson(gson.toJson(obj), Book.class);
            bookList.add(book);
        }

        assertThat(bookList.size()).isEqualTo(3);

    }

    @Test
    @DisplayName("시인 이름으로 시집 조회 테스트 / 등록순")
    void selectBooksByAuthorTest() throws Exception {
        // given
        String author = "백싸피";

        MvcResult mvcResult = mvc.perform(get("http://127.0.0.1:8080/bookSearch/bookSearchByAuthor?keyword=" + author)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Gson gson = new Gson();

        Type listType = new TypeToken<List<Object>>(){}.getType();
        List<Object> resultList = gson.fromJson(response, listType);
        List<Book> bookList = new ArrayList<>();
        for (Object obj : resultList) {
            Book book = gson.fromJson(gson.toJson(obj), Book.class);
            bookList.add(book);
        }

        assertThat(bookList.get(0).getTitle()).isEqualTo("so");

    }

}
