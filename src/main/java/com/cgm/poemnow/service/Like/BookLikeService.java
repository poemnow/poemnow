package com.cgm.poemnow.service.Like;

import com.cgm.poemnow.domain.Like.BookLike;

import java.util.HashMap;
import java.util.List;

public interface BookLikeService {

    int addBookLike(BookLike likeRequest);
    int removeBookLike(int userId, int bookId);
    List<HashMap<?, ?>> findBookLike(int userId);
    int findBookLikeUserCount(int userId);
    int findBookLikeBookCount(int bookId);
}