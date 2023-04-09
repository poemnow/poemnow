package com.cgm.poemnow.mapper;

import com.cgm.poemnow.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
	int insertUser(User user);
	User selectUserByIdentifierAndPassword(@Param("user_id") String user_id, @Param("password") String password);
	User getLoggedInUser(String user_id);
	User selectUserById(String user_id);
	int updateUser(User user);
	int deleteUser(User user);
}
