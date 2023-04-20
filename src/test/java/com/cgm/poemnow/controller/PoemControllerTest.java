package com.cgm.poemnow.controller;

import com.cgm.poemnow.domain.Poem;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PoemControllerTest {

    @Autowired
    PoemController poemController;

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("리스트 조회 테스트")
    void poemList() throws Exception{

        MvcResult mvcResult = mvc.perform(get("http://127.0.0.1:8080/poem/poemList"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String jsonResult = mvcResult.getResponse().getContentAsString();

        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = jsonParser.parse(jsonResult).getAsJsonArray();
        Gson gson = new GsonBuilder().disableHtmlEscaping().setDateFormat("yy-MM-dd hh;mm:ss").create();
        boolean isContained = false;
        for (JsonElement jsonElement : jsonArray) {
            Poem poem = gson.fromJson(jsonElement, Poem.class);
            if(1==(poem.getId())) {
                isContained = true;
                break;
            }
        }
        assertThat(isContained).isTrue();

    }

    @Test
    @DisplayName("시 추가")
    void poemAdd() throws Exception{

        // given
        Poem newPoem = Poem.builder()
                .title("1번 시")
                .content("1번")
                .userId(1)
                .font("11해체")
                .build();

        String jsonData = new Gson().toJson(newPoem);

        mvc.perform(post("http://127.0.0.1:8080/poem/poemAdd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData))
                .andExpect(status().isCreated()); // 아래에서 바로 조회까지 할테니 상태코드만 확인


            }


    @Test
    @DisplayName("시 id로 조회 테스트")
    void poemDetailTest() throws Exception {

        // given
        int id = 1;

        MvcResult mvcResult = mvc.perform(get("/poem/poemDetail/" + id))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        Gson gson = new Gson();
        Poem selectedPoem = gson.fromJson(response, Poem.class);

        assertThat(selectedPoem.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("시 수정 및 조회 확인 테스트")
    void poemUpdateTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        // given
        int id = 1;

        System.out.println(":::::::::: before update :::::::::");

        mvc.perform(get("/poem/poemDetail/" + id))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        // given
        Poem poem = new Poem();
        poem.setId(id);
        poem.setTitle("5555");
        poem.setContent("오오오오");
        poem.setFont("제발되어줘체");

        mvc.perform(put("/poem/poemModify/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(poem)))
                        .andExpect(status().isOk());

        mvc.perform(get("/poem/poemDetail/" + id))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

    }

    @Test
    @DisplayName("시 삭제 및 데이터 개수 변화 확인 테스트")
    void poemRemoveTest() throws Exception {

        // 전체조회하여 첫번째 데이터를 꺼내옵니다.
        MvcResult mvcResult1 = mvc.perform(get("/poem/poemList"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String jsonResult = mvcResult1.getResponse().getContentAsString();

        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = jsonParser.parse(jsonResult).getAsJsonArray();

        int id = jsonArray.get(0).getAsJsonObject().get("id").getAsInt();
        int size = jsonArray.size();

        // 첫번째 데이터를 삭제합니다.
        mvc.perform(delete("/poem/poemRemove/"+id))
                .andExpect(status().isOk());

        MvcResult mvcResult2 = mvc.perform(get("/poem/poemList"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String jsonResult2 = mvcResult2.getResponse().getContentAsString();

        // 전체 데이터갯수가 한개 줄었나 확인해줍니다.
        assertThat(jsonParser.parse(jsonResult2).getAsJsonArray().size()).isEqualTo(size-1);

    }

}