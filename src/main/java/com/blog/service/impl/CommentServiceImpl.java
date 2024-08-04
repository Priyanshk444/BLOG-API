package com.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.exceptions.ReourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.repositories.CommentRepository;
import com.blog.repositories.PostRepository;
import com.blog.service.CommentService;
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<CommentDto> getComment(Post post) {
		 List<Comment> byPost = commentRepository.findByPost(post);
		 List<CommentDto> CommDtos = byPost.stream().map((comm)->modelMapper.map(byPost,CommentDto.class)).collect(Collectors.toList());
		return CommDtos;
	}

	@Override
	public CommentDto postComment(CommentDto commentDto,Integer id) {
		Post post = postRepository.findById(id).orElseThrow(()->new ReourceNotFoundException("Post","Post Repository",id));
		Comment comment = new Comment();
		comment.setContent(commentDto.getContent());
		comment.setPost(post);
		Comment comment2 = commentRepository.save(comment);
		return modelMapper.map(comment2, CommentDto.class);
	}

	@Override
	public void delete(Integer commentID) {
		commentRepository.deleteById(commentID);
	}

}
