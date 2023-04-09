package com.cgm.poemnow.service;

import com.cgm.poemnow.domain.Like;
import com.cgm.poemnow.mapper.LikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeMapper likeMapper;
    @Override
    public int likePoem(Like likeRequest) {
        return likeMapper.likePoem(likeRequest);
    }

    @Override
    public int unLikePoem(int userId, int poemId) {
        return likeMapper.unLikePoem(userId, poemId);
    }

    @Override
    public List<HashMap<?, ?>> likePoemList(int userId) {
        return likeMapper.likePoemList(userId);
    }
}