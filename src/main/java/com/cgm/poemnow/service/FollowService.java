package com.cgm.poemnow.service;

import com.cgm.poemnow.domain.Follow;
import com.cgm.poemnow.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface FollowService {

	int addFollow(Follow followRequest);

	List<HashMap<String, String>> findFollow(int userId);

	int removeFollow( int userId , int followId);

	List<HashMap<String, String>> findFollower(int userId);

    int findFollowCnt(int userId);

	int findFollowerCnt(int userId);

	boolean loginUser(User user, HttpServletRequest request);

}
