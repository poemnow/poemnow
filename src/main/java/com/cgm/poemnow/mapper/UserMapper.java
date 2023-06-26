package com.cgm.poemnow.mapper;

import com.cgm.poemnow.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

	int insertUser(User user);
	User selectUserByIdentifierAndPassword(@Param("userId") String userId, @Param("password") String password);
	User getLoggedInUser(String userId);
	User selectUserByUserId(String userId);
	User selectUserById(int id);
	int updateUser(User user);
	int deleteUser(String userId);
	void updateLoggedIn (User user);
	List<User> selectUsersByNickname(String keyword, String sortOrder);

	int deleteUserReal(int id);

	User selectUserByEmail(String email);
}