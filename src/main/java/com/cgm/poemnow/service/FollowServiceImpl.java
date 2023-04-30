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
	public int addFollow(Follow followRequest) {
		return followMapper.insertFollow(followRequest);
	}

	@Override
	public List<HashMap<String, String>> findFollow(int userId) { return followMapper.selectFollow(userId);
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
	public boolean loginUser(User user, HttpServletRequest request) {
		User loginUser = followMapper.selectUserByIdentifierAndPassword(
				user.getUserId(),
				user.getPassword()
		);
		HttpSession session = request.getSession();
		session.setAttribute("loginUser",loginUser);
		return true;
	}

}
