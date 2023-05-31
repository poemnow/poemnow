package com.cgm.poemnow.service.comment;

import com.cgm.poemnow.domain.Comment;

import java.util.List;

public interface CommentService {

	List<Comment> findAllComments();

	int addComment(Comment comment);

	int modifyComment(Comment comment);

	int removeComment(int commentId);

	List<Comment> findCommentsByUserId(int userId);

	List<Comment> findCommentsOfThePoem(int poemId);

	int removeCommentsByUserId(int userId);

}
