package com.cgm.poemnow.mapper;


import com.cgm.poemnow.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface  UserSearchMapper {
    List<User> selectUsersByNickname(String keyword, String sortOrder);
}
