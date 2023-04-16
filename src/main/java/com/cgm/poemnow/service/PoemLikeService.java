package com.cgm.poemnow.service;

import com.cgm.poemnow.domain.PoemLike;

import java.util.HashMap;
import java.util.List;

public interface PoemLikeService {

    int addPoemLike(PoemLike poemLikeRequest);

    int removePoemLike(int userId, int poemId);

    List<HashMap<?, ?>> findPoemLike(int userId);

}