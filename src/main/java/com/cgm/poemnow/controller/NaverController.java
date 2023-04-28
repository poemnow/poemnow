package com.cgm.poemnow.controller;

import com.cgm.poemnow.domain.User;
import com.cgm.poemnow.service.NaverLoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.support.CompositeUriComponentsContributor;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

@RestController
@RequestMapping("/login")
public class NaverController {

	@Autowired
	private NaverLoginService naverLoginService;
	@Autowired
	private CompositeUriComponentsContributor uri;

	@GetMapping("/naverLogin")
	public  void naverConnect(HttpServletResponse response) throws IOException {
		//state 용 난수 생성
		SecureRandom random = new SecureRandom();
		String state = new BigInteger(130, random).toString(32);

		//redirect
		StringBuffer url = new StringBuffer();
		url.append("https://nid.naver.com/oauth2.0/authorize?");
		url.append("client_id=cf5PAbTrDyD9g5jgqQTn");
		url.append("&response_type=code");
		url.append("&redirect_uri=http://localhost:8080/login/naverCallback");
		url.append("&state=" + state);

		response.sendRedirect(String.valueOf(url));
	}

	@GetMapping(path = "/naverCallback")
	public ResponseEntity<String> kakaoCallback(@RequestParam String state, @RequestParam String code, HttpSession session) {
		String token = naverLoginService.getToken(state, code);
		String id = naverLoginService.getUserInfo(token);
		System.out.println(id);
		User user = naverLoginService.getUser(id);
		if(user == null){
			//회원가입 페이지로 보내!!
			return new ResponseEntity<>(id, HttpStatus.BAD_REQUEST);
		}else{
			//메인으로 보내!!
			session.setAttribute("loginUser" , user);
			return new ResponseEntity<>(id, HttpStatus.OK);
		}
	}

}
