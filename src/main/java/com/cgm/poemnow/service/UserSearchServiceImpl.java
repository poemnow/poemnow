package com.cgm.poemnow.service;

import com.cgm.poemnow.domain.User;
import com.cgm.poemnow.mapper.UserSearchMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSearchServiceImpl implements UserSearchService {

    @Autowired
    private UserSearchMapper userSearchMapper;

    @Override
    public List<User> findUsersByNickname(String keyword, String sortOrder) {
        return userSearchMapper.selectUsersByNickname(keyword, sortOrder);
    }
}
