package com.cgm.poemnow.service.Like;

import com.cgm.poemnow.domain.Like.CommentLike;
import com.cgm.poemnow.domain.User;
import com.cgm.poemnow.mapper.CommentLikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class CommentLikeServiceImpl implements CommentLikeService {

    @Autowired
    private CommentLikeMapper commentLikeMapper;
    @Override
    public int addCommentLike(CommentLike likeRequest) {
        return commentLikeMapper.insertCommentLike(likeRequest);
    }

    @Override
    public int removeCommentLike(int userId, int commentId) {
        return commentLikeMapper.deleteCommentLike(userId, commentId);
    }

    @Override
    public List<HashMap<?, ?>> findCommentLike(int userId) {
        return commentLikeMapper.selectCommentLike(userId);
    }

    @Override
    public boolean loginUser(User user, HttpServletRequest request) {
        User loginUser = commentLikeMapper.selectUserByIdentifierAndPassword(
                user.getUserId(),
                user.getPassword()
        );
        HttpSession session = request.getSession();
        session.setAttribute("loginUser",loginUser.getId());
        return true;
    }

}