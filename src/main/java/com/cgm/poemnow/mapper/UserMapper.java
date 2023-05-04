package com.cgm.poemnow.mapper;

import java.util.List;

import com.cgm.poemnow.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

	int insertUser(User user);
	User selectUserByIdentifierAndPassword(@Param("userId") String userId, @Param("password") String password);
	User getLoggedInUser(String userId);
	User selectUserById(String userId);
	int updateUser(User user);
	int deleteUser(String userId);
	void updateLoggedIn (User user);
	List<User> selectUsersByNickname(String keyword, String sortOrder);

}