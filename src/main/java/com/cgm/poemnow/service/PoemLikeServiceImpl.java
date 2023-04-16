package com.cgm.poemnow.service;

import com.cgm.poemnow.domain.PoemLike;
import com.cgm.poemnow.mapper.PoemLikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class PoemLikeServiceImpl implements PoemLikeService {

    @Autowired
    private PoemLikeMapper poemLikeMapper;

    @Override
    public int addPoemLike(PoemLike poemLikeRequest) {
        return poemLikeMapper.insertPoemLike(poemLikeRequest);
    }

    @Override
    public int removePoemLike(int userId, int poemId) {
        return poemLikeMapper.deletePoemLike(userId, poemId);
    }

    @Override
    public List<HashMap<?, ?>> findPoemLike(int userId) {
        return poemLikeMapper.selectPoemLike(userId);
    }

}