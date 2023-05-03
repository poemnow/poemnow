package com.cgm.poemnow.controller;

import com.cgm.poemnow.domain.CommentLike;
import com.cgm.poemnow.service.CommentLikeService;
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
class CommentLikeControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    CommentLikeService commentLikeService;
    @Autowired
    CommentLikeController commentlikeController;
    @Test
    //콘솔창에 무슨 테스트 인지 띄어줌
    @DisplayName("댓글 좋아요 추가 및 리스트 조회 테스트")
    void commentLikeAddAndcommentLikeList() throws Exception {
        // given 데이터를 보내줄 애를 만들자
        //Follow 객체를 넣으니 만들자
        CommentLike newLike = CommentLike.builder()
                .userId(1)
                .commentId(2)
                .build();
        //그 객체 제이슨으로 바꾸자
        String jsonData = new Gson().toJson(newLike);

        mvc.perform(post("http://127.0.0.1:8080/commentlike/CommentLikeAdd")
                        .contentType(MediaType.APPLICATION_JSON) //타입설정
                        .content(jsonData)) //제이슨으로 바꾼 내용물
                .andExpect(status().isCreated()); // 이 실행결과가 Create면 된다

        //내가 팔로우 하는 사람 조회 테스트
        String userId="1";
        MvcResult mvcResult = mvc.perform(get("http://127.0.0.1:8080/commentlike/likeCommentList")
                        .contentType(MediaType.APPLICATION_JSON) //타입설정
                        .content(userId))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        //mvcResult에 넣은 애 중 String인 것(select 해온 것을) jsonResult에 넣을게
        String jsonResult = mvcResult.getResponse().getContentAsString();

        JsonParser jsonParser = new JsonParser();
        //jsonresult에 있는 거를 잘라서 배열에 넣어
        JsonArray jsonArray = jsonParser.parse(jsonResult).getAsJsonArray();
        Gson gson = new GsonBuilder().disableHtmlEscaping().setDateFormat("yy-MM-dd hh;mm:ss").create();
        boolean isContained = false;
        //배열에 있는거를 하나씩 jsonElement에 넣어
        //그걸 객체로 변환시켜서 넣어
        for (JsonElement jsonElement : jsonArray) {
            CommentLike likes = gson.fromJson(jsonElement, CommentLike.class);
            if (2 == (likes.getCommentId())) {
                isContained = true;
                break;
            }
        }
        assertThat(isContained).isTrue();
    }

    @Test
    @DisplayName("댓글 좋아요 삭제 및 데이터 개수 변화 확인 테스트")
    void poemLikeRemoveTest() throws Exception {

        // 전체조회하여 첫번째 데이터를 꺼내옵니다.
        MvcResult mvcResult1 = mvc.perform(get("/commentlike/likeCommentList"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String jsonResult = mvcResult1.getResponse().getContentAsString();

        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = jsonParser.parse(jsonResult).getAsJsonArray();

        int id = jsonArray.get(0).getAsJsonObject().get("commentId").getAsInt();
        int size = jsonArray.size();

        // 첫번째 데이터를 삭제합니다.
        mvc.perform(delete("/commentlike/CommentLikeRemove?commentId="+id))
                .andExpect(status().isOk());

        MvcResult mvcResult2 = mvc.perform(get("/commentlike/likeCommentList"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String jsonResult2 = mvcResult2.getResponse().getContentAsString();

        // 전체 데이터갯수가 한개 줄었나 확인해줍니다.
        assertThat(jsonParser.parse(jsonResult2).getAsJsonArray().size()).isEqualTo(size-1);

    }


}