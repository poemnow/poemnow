package com.cgm.poemnow.mapper;

import com.cgm.poemnow.domain.Like.BookLike;
import com.cgm.poemnow.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface BookLikeMapper {

    int insertBookLike(BookLike likeRequest);
    int deleteBookLike(int userId, int bookId);
    List<HashMap<?, ?>> selectBookLike(int userId);
    User selectUserByIdentifierAndPassword(String userId, String password);
}