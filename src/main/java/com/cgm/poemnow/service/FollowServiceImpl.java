package com.cgm.poemnow.service;

import com.cgm.poemnow.domain.Follow;
import com.cgm.poemnow.domain.User;
import com.cgm.poemnow.mapper.FollowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class FollowServiceImpl implements FollowService {

	@Autowired
	private FollowMapper followMapper;

	@Override
	public int addFollow(int userId , int followId) {
		return followMapper.insertFollow(userId, followId);
	}

	@Override
	public List<HashMap<String, String>> findMyFollow(int userId) { return followMapper.selectFollow(userId);
	}

	@Override
	public int removeFollow( int userId , int followId) {
		return followMapper.deleteFollow(userId, followId);
	}

	@Override
	public List<HashMap<String, String>> findFollower(int userId) {
		return followMapper.selectFollower(userId);
	}

	@Override
	public int findFollowCnt(int userId) {
		return followMapper.selectFollowCnt(userId);
	}

	@Override
	public int findFollowerCnt(int userId) {
		return followMapper.selectFollowerCnt(userId);
	}

	@Override
	public List<HashMap<String, String>> findYourFollowSame(int id , int userId) {
		return followMapper.selectYourFollowSame(id , userId);
	}

	@Override
	public List<HashMap<String, String>> findYourFollowDif(int id, int userId) {
		return followMapper.selectYourFollowDif(id , userId);
	}

	@Override
	public List<HashMap<String, String>> findYourFollowerSame(int id, int userId) {
		return followMapper.selectYourFollowerSame(id, userId);
	}

	@Override
	public List<HashMap<String, String>> findYourFollowerDif(int id, int userId) {
		return followMapper.selectYourFollowerDif(id, userId);
	}

}
