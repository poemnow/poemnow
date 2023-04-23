package com.cgm.poemnow.service;

import java.util.List;

import com.cgm.poemnow.domain.User;

public interface UserSearchService {

    List<User> findUsersByNickname(String keyword, String sortOrder);

}
