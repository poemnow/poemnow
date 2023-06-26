package com.cgm.poemnow.controller;

import com.cgm.poemnow.service.user.EmailService;
import com.cgm.poemnow.service.user.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/service")
public class EmailController {
	@Autowired
	EmailService service;

	@PostMapping("/mail")
	@ResponseBody
	public void emailConfirm(@RequestParam String email)throws Exception{
		service.sendSimpleMessage(email);
	}
	@PostMapping("/verifyCode")
	@ResponseBody
	public int verifyCode(@RequestParam String code) {
		int result = 0;
		if(EmailServiceImpl.ePw.equals(code)) {
			result =1;
		}

		return result; //responseBody result 1일시 이메일 인증 됨, 0일시 인증 실패(틀리게 입력)
	}
}