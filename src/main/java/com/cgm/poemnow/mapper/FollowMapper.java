package com.cgm.poemnow.mapper;

import com.cgm.poemnow.domain.Follow;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface FollowMapper {

	int insertFollow(Follow followRequest);

	List<HashMap<String, String>> listFollow(int userId);

	int removeFollow( int userId , int followId);

	List<HashMap<String, String>> listFollower(int userId);
}
