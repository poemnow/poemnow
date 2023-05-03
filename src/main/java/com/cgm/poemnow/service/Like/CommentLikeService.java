package com.cgm.poemnow.service.Like;

import com.cgm.poemnow.domain.Like.CommentLike;
import com.cgm.poemnow.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface CommentLikeService {

    int addCommentLike(CommentLike likeRequest);
    int removeCommentLike(int userId, int commentId);
    List<HashMap<?, ?>> findCommentLike(int userId);

    boolean loginUser(User user, HttpServletRequest request);
}