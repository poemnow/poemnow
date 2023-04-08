package com.cgm.poemnow.service;

import com.cgm.poemnow.domain.Follow;
import com.cgm.poemnow.mapper.FollowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class FollowServiceImpl implements FollowService {

	@Autowired
	private FollowMapper followMapper;


	@Override
	public int addFollow(Follow followRequest) {
		return followMapper.insertFollow(followRequest);
	}

	@Override
	public List<HashMap<String, String>> listFollow(int userId) {
		return followMapper.listFollow(userId);
	}

	@Override
	public int removeFollow( int userId , int followId) {
		return followMapper.removeFollow(userId, followId);
	}

	@Override
	public List<HashMap<String, String>> listFollower(int userId) {
		return followMapper.listFollower(userId);
	}
}
