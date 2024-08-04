package com.blog.service;

import java.util.List;

import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto,Integer userId,Integer catId);
	
	PostDto updatePost(PostDto postDto,Integer id);
	
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDirn);
	
	PostDto getPostByID(Integer id);
	
	void deletePost(Integer id);
	
	List<PostDto> getPostByUser(Integer userId);
	
	
	List<PostDto> getPostByCategory(Integer catId);
	
	List<PostDto> searchPost(String keyword) ;
}
