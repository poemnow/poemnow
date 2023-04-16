package com.cgm.poemnow.mapper;

import com.cgm.poemnow.domain.CommentLike;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface CommentLikeMapper {

    int insertCommentLike(CommentLike likeRequest);
    int deleteCommentLike(int userId, int commentId);
    List<HashMap<?, ?>> selectCommentLike(int userId);

}