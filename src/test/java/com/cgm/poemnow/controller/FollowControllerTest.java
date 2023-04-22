package com.cgm.poemnow.controller;

import com.cgm.poemnow.domain.Follow;
import com.cgm.poemnow.domain.User;
import com.cgm.poemnow.service.FollowService;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class FollowControllerTest {
    @Autowired
    MockMvc mvc;



    @Test
    //콘솔창에 무슨 테스트 인지 띄어줌
    @DisplayName("팔로우 추가 및 리스트 조회 테스트")
    @Transactional
    void followAddAndFollowList() throws Exception {
        // given 데이터를 보내줄 애를 만들자
        //Follow 객체를 넣으니 만들자
        Follow newFollow = Follow.builder()
                .userId(1)
                .followId(3)
                .build();
        //그 객체 제이슨으로 바꾸자
        String jsonData = new Gson().toJson(newFollow);

        mvc.perform(post("http://127.0.0.1:8080/follow/followAdd")
                        .contentType(MediaType.APPLICATION_JSON) //타입설정
                        .content(jsonData)) //제이슨으로 바꾼 내용물
                .andExpect(status().isCreated()); // 이 실행결과가 Create면 된다

        //내가 팔로우 하는 사람 조회 테스트
        String userId="1";
        MvcResult mvcResult = mvc.perform(get("http://127.0.0.1:8080/follow/followList")
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
            User users = gson.fromJson(jsonElement, User.class);
            if ("wns".equals(users.getUserId())) {
                    isContained = true;
                    break;
            }
        }
        assertThat(isContained).isTrue();
    }
    @Test
    @DisplayName("팔로우 삭제 테스트")
    void followRemove() {
        // given
        int followId = 2;
        int userId = 1;
        int response = 1;
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(userId);
        when(followService.removeFollow(userId, followId)).thenReturn(response);
        ResponseEntity<?> result = followController.followRemove(followId, request);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Mock
    private FollowService followService;

    @Mock
    private HttpSession session;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private FollowController followController;
    @Test
    @DisplayName("팔로워 조회 테스트")
    void followerList() {
        int userId = 1;
        List<HashMap<String, String>> response = new ArrayList<>();
        HashMap<String, String> follower1 = new HashMap<>();
        follower1.put("id", "2");
        follower1.put("name", "John Doe");
        response.add(follower1);
        HashMap<String, String> follower2 = new HashMap<>();
        follower2.put("id", "3");
        follower2.put("name", "Jane Smith");
        response.add(follower2);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(userId);
        when(followService.findFollower(userId)).thenReturn(response);
        ResponseEntity<List<HashMap<String, String>>> result = followController.followerList(request);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    @DisplayName("팔로우 수 구하기 테스트")
    void testFollowCnt() {
        int userId = 1;
        int response = 2;
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(userId);
        when(followService.findFollowCnt(userId)).thenReturn(response);
        ResponseEntity<Integer> result = followController.followCnt(request);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }
    @Test
    @DisplayName("팔로워 수 구하기")
    void testFollowerCnt() {
        int userId = 1;
        int response = 2;
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(userId);
        when(followService.findFollowerCnt(userId)).thenReturn(response);
        ResponseEntity<Integer> result = followController.followerCnt(request);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }
}