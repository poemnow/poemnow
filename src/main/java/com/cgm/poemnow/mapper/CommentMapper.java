package com.cgm.poemnow.mapper;

import com.cgm.poemnow.domain.Comment;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {
	int insertComment(Comment comment);

	List<Comment> selectAllComments();

	int updateComment(Comment comment);

	int deleteComment(int id);
}
