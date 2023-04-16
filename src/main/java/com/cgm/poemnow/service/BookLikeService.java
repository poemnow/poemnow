package com.cgm.poemnow.service;

import com.cgm.poemnow.domain.BookLike;

import java.util.HashMap;
import java.util.List;

public interface BookLikeService {

    int addBookLike(BookLike likeRequest);
    int removeBookLike(int userId, int bookId);
    List<HashMap<?, ?>> findBookLike(int userId);

}