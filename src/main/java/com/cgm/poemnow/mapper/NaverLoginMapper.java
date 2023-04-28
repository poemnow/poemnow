package com.cgm.poemnow.mapper;

import com.cgm.poemnow.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface NaverLoginMapper {

	User getUser(String id);

}
