package com.cgm.poemnow.service;

import com.cgm.poemnow.domain.User;

public interface KaKaoLoginService {

	String getKakaoAccessToken (String code);

	String createKakaoUser(String token);

	User getUser(String id);

}
