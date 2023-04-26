package com.cgm.poemnow.mapper;

import com.cgm.poemnow.domain.Like.PoemLike;
import com.cgm.poemnow.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface PoemLikeMapper {

    int insertPoemLike(PoemLike poemLikeRequest);

    int deletePoemLike(int userId, int poemId);

    List<HashMap<?, ?>> selectPoemLike(int userId);

    User selectUserByIdentifierAndPassword(String userId, String password);

}