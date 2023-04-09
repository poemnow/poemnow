package com.cgm.poemnow.mapper;

import com.cgm.poemnow.domain.Like;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface LikeMapper {
    int likePoem(Like likeRequest);
    int unLikePoem(int userId, int poemId);
    List<HashMap<?, ?>> likePoemList(int userId);
}