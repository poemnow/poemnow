package com.cgm.poemnow.service;

import com.cgm.poemnow.domain.Follow;
import com.cgm.poemnow.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface FollowService {

	int addFollow(int userId , int followId);

	List<HashMap<String, String>> findMyFollow(int userId);

	int removeFollow( int userId , int followId);

	List<HashMap<String, String>> findFollower(int userId);

    int findFollowCnt(int userId);

	int findFollowerCnt(int userId);

	boolean loginUser(User user, HttpServletRequest request);

	List<HashMap<String, String>> findYourFollowSame(int id , int userId);

	List<HashMap<String, String>> findYourFollowDif(int id, int userId);
}
