package com.cgm.poemnow.service.Like;

import com.cgm.poemnow.domain.Like.PoemLike;
import com.cgm.poemnow.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface PoemLikeService {

    int addPoemLike(PoemLike poemLikeRequest);

    int removePoemLike(int userId, int poemId);

    List<HashMap<?, ?>> findPoemLike(int userId);
    int findPoemLikeUserCount(int userId);
    int findPoemLikePoemCount(int poemId);

}