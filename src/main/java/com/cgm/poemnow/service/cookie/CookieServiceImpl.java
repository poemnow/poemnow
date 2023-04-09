package com.cgm.poemnow.service.cookie;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class CookieServiceImpl implements CookieService {

	private final static String COOKIE_DOMAIN = "poemnow.com";
	private final static String COOKIE_NAME = "user_cookie";

	@Override
	public void setCookie(HttpServletResponse response, String value, int maxAge) {
		// 쿠키 생성
		Cookie cookie = new Cookie(COOKIE_NAME, value);

		// 도메인 설정
		cookie.setDomain(COOKIE_DOMAIN);

		// Path 설정
		cookie.setPath("/");

		// 만료 기간 설정
		cookie.setMaxAge(maxAge);

		// HttpOnly 설정 (JavaScript에서 쿠키에 접근할 수 없게 함)
		cookie.setHttpOnly(true);

		// 쿠키를 response에 추가
		response.addCookie(cookie);
	}

	@Override
	public String getCookie(HttpServletRequest request) {
		// Request에서 쿠키 배열을 가져옴
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			// 쿠키 배열에서 'user_cookie' 이름을 가진 쿠키를 찾음
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(COOKIE_NAME)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	@Override
	public void deleteCookie(HttpServletResponse response) {
		// 쿠키 생성
		Cookie cookie = new Cookie(COOKIE_NAME, "");

		// 도메인 설정
		cookie.setDomain(COOKIE_DOMAIN);

		// Path 설정
		cookie.setPath("/");

		// 만료 기간 설정 (0이면 즉시 삭제)
		cookie.setMaxAge(0);

		// HttpOnly 설정 (JavaScript에서 쿠키에 접근할 수 없게 함)
		cookie.setHttpOnly(true);

		// 쿠키를 response에 추가
		response.addCookie(cookie);
	}
}