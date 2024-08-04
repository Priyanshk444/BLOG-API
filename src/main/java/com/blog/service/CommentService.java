package com.blog.service;

import java.util.List;

import com.blog.entities.Post;
import com.blog.payload.CommentDto;

public interface CommentService {

	List<CommentDto> getComment(Post post);
	
	CommentDto postComment(CommentDto commentDto,Integer id);
	
	void delete(Integer commentID);
}
