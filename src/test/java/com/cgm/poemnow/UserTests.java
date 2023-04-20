package com.cgm.poemnow;

import com.cgm.poemnow.domain.User;
import com.cgm.poemnow.mapper.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class UserTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserMapper userMapper;

	@Test
	public void testAddUser() throws Exception {
		User user = new User();
		// 테스트 전에 아래의 testId를 중복되지 않게 만들어주세요!!!
		int testId = 2;
		user.setUserId("test_id" + testId);
		user.setPassword("test_password");
		user.setEmail("test" + testId + "@gmail.com");
		user.setUserNickname("test_nickname" + testId);
		user.setUserName("test_name" + testId);
		user.setUserBirth(new Timestamp(System.currentTimeMillis()));

		mockMvc.perform(post("/user/register")
			.contentType(MediaType.APPLICATION_JSON)
			.content(new ObjectMapper().writeValueAsString(user)))
			.andExpect(status().isCreated());

		User savedUser = userMapper.selectUserById(user.getUserId());
		System.out.println(savedUser);
		assertNotNull(savedUser);
		assertEquals(user.getUserId(), savedUser.getUserId());
	}

	@Test
	public void testFindUserById() throws Exception {
		// 테스트 전에 아래의 testId를 존재하는 사용자로 찾아주세요!!!
		int testId = 2;
		String userId = "test_id" + testId;

		MvcResult result = mockMvc.perform(get("/user/profile/" + userId))
			.andExpect(status().isAccepted())
			.andDo(print())
			.andReturn();

		String content = result.getResponse().getContentAsString();
		System.out.println(content);
	}

	@Test
	public void testUserModify() throws Exception {
		User user = new User();
		user.setUserId("test_id1");
		user.setUserNickname("test_nickname3");
		user.setProfilePic("test_profile_pic");
		user.setBio("test_bio");
		user.setSocialUrl("test_social_url");

		mockMvc.perform(post("/user/profile/")
			.contentType(MediaType.APPLICATION_JSON)
			.content(new ObjectMapper().writeValueAsString(user)))
			.andExpect(status().isAccepted())
			.andReturn();
	}
	@Test
	public void testUserWithdraw() throws Exception {
		String userId = "test_id2";
		mockMvc.perform(post("/user/withdraw/" + userId))
			.andExpect(status().isAccepted());
	}

}
