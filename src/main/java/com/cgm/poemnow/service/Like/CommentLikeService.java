package com.cgm.poemnow.service.Like;

import com.cgm.poemnow.domain.Like.CommentLike;

import java.util.HashMap;
import java.util.List;

public interface CommentLikeService {

    int addCommentLike(CommentLike likeRequest);
    int removeCommentLike(int userId, int commentId);
    List<HashMap<?, ?>> findCommentLike(int userId);
    int findCommentLikeCnt(int commentId);

    int findCommentLikeUserCount(int commentId);
}