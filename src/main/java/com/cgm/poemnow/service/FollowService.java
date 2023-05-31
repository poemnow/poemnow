package com.cgm.poemnow.service;

import java.util.HashMap;
import java.util.List;

public interface FollowService {

	int addFollow(int userId , int followId);

	List<HashMap<String, String>> findMyFollow(int userId);

	int removeFollow( int userId , int followId);

	List<HashMap<String, String>> findFollower(int userId);

    int findFollowCnt(int userId);

	int findFollowerCnt(int userId);

	List<HashMap<String, String>> findYourFollowSame(int id , int userId);

	List<HashMap<String, String>> findYourFollowDif(int id, int userId);

	List<HashMap<String, String>> findYourFollowerSame(int parseInt, int id);

	List<HashMap<String, String>> findYourFollowerDif(int parseInt, int id);
}
