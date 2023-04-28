package com.cgm.poemnow.service;

import com.cgm.poemnow.domain.User;

public interface NaverLoginService {

	String getUserInfo(String token);

	String getToken(String state, String code);

	User getUser(String id);

}
