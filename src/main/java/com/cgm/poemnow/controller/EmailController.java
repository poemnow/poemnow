package com.cgm.poemnow.controller;

import com.cgm.poemnow.service.user.EmailServiceImpl;
import com.cgm.poemnow.service.user.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/service/*")
public class EmailController {
	@Autowired
	EmailService service;

	@PostMapping("/mail")
	@ResponseBody
	public void emailConfirm(String email)throws Exception{
		service.sendSimpleMessage(email);
	}
	@PostMapping("/verifyCode")
	@ResponseBody
	public int verifyCode(String code) {

		int result = 0;
		if(EmailServiceImpl.ePw.equals(code)) {
			result =1;
		}

		return result;
	}
}