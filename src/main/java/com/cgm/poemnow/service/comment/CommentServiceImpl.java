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
	public List<Comment> findAllComments() {
		return commentMapper.selectAllComments();
	}
	@Override
	public int addComment(Comment comment) {
		return commentMapper.insertComment(comment);
	}

	@Override
	public int modifyComment(Comment comment) {
		return commentMapper.updateComment(comment);
	}

	@Override
	public int removeComment(int commentId) {
		return commentMapper.deleteComment(commentId);
	}

	@Override
	public List<Comment> findCommentsByUserId(int userId) {
		return commentMapper.selectCommentsByUserId(userId);
	}

	@Override
	public List<Comment> findCommentsOfThePoem(int poemId){
		return commentMapper.selectCommentsOfThePoem(poemId);
	}
	@Override
	public int removeCommentsByUserId(int userId) {
		return commentMapper.deleteCommentsByUserId(userId);
	}

	@Override
	public int findCommentCount(int poemId) {
		return commentMapper.selectCommentCountByPoem(poemId);
	}

}
