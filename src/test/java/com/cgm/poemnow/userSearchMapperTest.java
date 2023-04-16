package com.cgm.poemnow;

import com.cgm.poemnow.controller.PoemSearchController;

import com.cgm.poemnow.domain.User;

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
public class userSearchMapperTest {
    @Autowired
    PoemSearchController poemSearchController;

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("유저 닉네임으로 유저 조회 테스트 / 등록순")
    void selectUsersByNicknameTest() throws Exception {
        // given
        String title = "콩덕후";

        MvcResult mvcResult = mvc.perform(get("http://127.0.0.1:8080/userSearch/userSearchByNickname?keyword=" + title)
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
        List<User> userList = new ArrayList<>();
        for (Object obj : resultList) {
            User user = gson.fromJson(gson.toJson(obj), User.class);
            userList.add(user);
        }

        assertThat(userList.get(0).getId()).isEqualTo(2);

    }


}
