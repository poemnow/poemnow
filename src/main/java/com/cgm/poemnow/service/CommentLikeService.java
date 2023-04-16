package com.cgm.poemnow.service;

import com.cgm.poemnow.domain.CommentLike;

import java.util.HashMap;
import java.util.List;

public interface CommentLikeService {

    int addCommentLike(CommentLike likeRequest);
    int removeCommentLike(int userId, int commentId);
    List<HashMap<?, ?>> findCommentLike(int userId);

}