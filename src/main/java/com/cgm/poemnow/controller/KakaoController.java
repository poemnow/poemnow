package com.cgm.poemnow.controller;

import com.cgm.poemnow.domain.User;
import com.cgm.poemnow.service.KaKaoLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/login")
public class KakaoController {

	@Autowired
	private KaKaoLoginService kaKaoLoginService;


	@GetMapping(value = "/oauth")
	public ResponseEntity<String> kakaoConnect(HttpServletResponse response) throws IOException {
		StringBuffer url = new StringBuffer();
		url.append("https://kauth.kakao.com/oauth/authorize?");
		url.append("client_id=" + "053162a8f6b7ba902d0d94786b63a656");
		url.append("&redirect_uri=http://localhost:8080/login/kakaoCallback");
		url.append("&response_type=code");
		return ResponseEntity.ok(url.toString());
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(path = "/kakaoCallback")
	public ResponseEntity<String> kakaoCallback(@RequestParam String code, HttpSession session) {

		String response = "";
		String token = kaKaoLoginService.getKakaoAccessToken(code);
		String id = kaKaoLoginService.createKakaoUser(token);
		System.out.println("id is " + id);
		response = id;
		return new ResponseEntity<>(response, HttpStatus.OK);
//		User user = kaKaoLoginService.getUser(id);
//		if(user == null){
//			//회원가입 페이지로 보내!!
//			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//		}else{
//			session.setAttribute("loginUser" , user);
//			//메인으로 보내!!
//			return new ResponseEntity<>(response, HttpStatus.OK);
//		}
	}

}
