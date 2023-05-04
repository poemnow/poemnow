package com.cgm.poemnow.service.Like;

import com.cgm.poemnow.domain.Like.BookLike;
import com.cgm.poemnow.mapper.BookLikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class BookLikeServiceImpl implements BookLikeService {

    @Autowired
    private BookLikeMapper booklikeMapper;
    @Override
    public int addBookLike(BookLike likeRequest) {
        return booklikeMapper.insertBookLike(likeRequest);
    }

    @Override
    public int removeBookLike(int userId, int bookId) {
        return booklikeMapper.deleteBookLike(userId, bookId);
    }

    @Override
    public List<HashMap<?, ?>> findBookLike(int userId) {
        return booklikeMapper.selectBookLike(userId);
    }

    @Override
    public int findBookLikeUserCount(int userId) {
        return booklikeMapper.selectBookLikeUserCount(userId);
    }

    @Override
    public int findBookLikeBookCount(int bookId) {
        return booklikeMapper.selectBookLikeBookCount(bookId);
    }


}