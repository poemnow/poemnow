package com.cgm.poemnow.service;

import com.cgm.poemnow.domain.Like;

import java.util.HashMap;
import java.util.List;

public interface LikeService {

    int likePoem(Like likeRequest);
    int unLikePoem(int userId, int poemId);
    List<HashMap<?, ?>> likePoemList(int userId);
}