package com.cgm.poemnow.mapper;

import com.cgm.poemnow.domain.Follow;
import com.cgm.poemnow.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface FollowMapper {

	int insertFollow(int userId , int followId);

	List<HashMap<String, String>> selectFollow(int userId);

	int deleteFollow( int userId , int followId);

	List<HashMap<String, String>> selectFollower(int userId);

    int selectFollowCnt(int userId);

	int selectFollowerCnt(int userId);

	User selectUserByIdentifierAndPassword(String userId, String password);

	List<HashMap<String, String>> selectYourFollowSame(int id , int userId);

	List<HashMap<String, String>> selectYourFollowDif(int id, int userId);

	List<HashMap<String, String>> selectYourFollowerSame(int id, int userId);

	List<HashMap<String, String>> selectYourFollowerDif(int id, int userId);

	int deleteFollower(int userId, int followId);
}
