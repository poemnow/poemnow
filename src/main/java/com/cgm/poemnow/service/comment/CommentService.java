package com.cgm.poemnow.service.comment;

import com.cgm.poemnow.domain.Comment;

import java.util.List;

public interface CommentService {

	int addComment(Comment comment);

	List<Comment> findAllComments();

	int modifyComment(Comment comment);

	int removeComment(int id);

}
