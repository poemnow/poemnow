package com.cgm.poemnow.service.Like;

import com.cgm.poemnow.domain.Like.BookLike;
import com.cgm.poemnow.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public interface BookLikeService {

    int addBookLike(BookLike likeRequest);
    int removeBookLike(int userId, int bookId);
    List<HashMap<?, ?>> findBookLike(int userId);

    boolean loginUser(User user, HttpServletRequest request);

}