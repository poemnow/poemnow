package com.cgm.poemnow.controller;

import com.cgm.poemnow.service.user.EmailService;
import com.cgm.poemnow.service.user.EmailServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class EmailControllerTest {
	@Autowired
	EmailController emailController;

	@Autowired
	EmailService emailService;

	@Autowired
	MockMvc mvc;

	@Test
	@Transactional
	@DisplayName("메일 보내기 - 옳게 입력 시, 틀리게 입력 시 테스트")
	void emailConfirm() throws Exception{

		// given
		emailController.emailConfirm("jihyean2004@naver.com");

		System.out.println(emailController.verifyCode(EmailServiceImpl.ePw));
		System.out.println(emailController.verifyCode("wrong123"));
	}

}
