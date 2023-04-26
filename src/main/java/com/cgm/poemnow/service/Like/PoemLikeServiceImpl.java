package com.cgm.poemnow.service.Like;

import com.cgm.poemnow.domain.Like.PoemLike;
import com.cgm.poemnow.domain.User;
import com.cgm.poemnow.mapper.PoemLikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @Override
    public boolean loginUser(User user, HttpServletRequest request) {
        System.out.println(user.getUserId() + user.getPassword() );
        User loginUser = poemLikeMapper.selectUserByIdentifierAndPassword(
                user.getUserId(),
                user.getPassword()
        );
        HttpSession session = request.getSession();
        session.setAttribute("loginUser",loginUser);
        return true;
    }
}