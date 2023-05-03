package com.cgm.poemnow.service.comment;

import com.cgm.poemnow.domain.Comment;
import com.cgm.poemnow.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentMapper commentMapper;

	@Override
	public int addComment(Comment comment) {
		return commentMapper.insertComment(comment);
	}

	@Override
	public List<Comment> findAllComments() {
		return commentMapper.selectAllComments();
	}

	@Override
	public int modifyComment(Comment comment) {
		return commentMapper.updateComment(comment);
	}

	@Override
	public int removeComment(Comment comment) {
		return commentMapper.deleteComment(comment);
	}

	@Override
	public List<Comment> findCommentsOfThePoem(int poemId){
		return commentMapper.selectCommentsOfThePoem(poemId);
	}
	@Override
	public int removeCommentsByUserId(int userId) {
		return commentMapper.deleteCommentsByUserId(userId);
	}

}
