package com.cgm.poemnow.service.cookie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CookieService {

	public void setCookie(HttpServletResponse response, String value, int maxAge);
	public String getCookie(HttpServletRequest request);
	public void deleteCookie(HttpServletResponse response);

}
