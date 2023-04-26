package com.cgm.poemnow.service.Like;

import com.cgm.poemnow.domain.Like.BookLike;
import com.cgm.poemnow.domain.User;
import com.cgm.poemnow.mapper.BookLikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public boolean loginUser(User user, HttpServletRequest request) {
        User loginUser = booklikeMapper.selectUserByIdentifierAndPassword(
                user.getUserId(),
                user.getPassword()
        );
        HttpSession session = request.getSession();
        session.setAttribute("userId",loginUser.getId());
        return true;
    }
}