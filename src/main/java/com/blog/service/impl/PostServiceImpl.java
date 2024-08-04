package com.blog.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entities.Categories;
import com.blog.entities.MyUsers;
import com.blog.entities.Post;
import com.blog.exceptions.ReourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.repositories.CategoryRepository;
import com.blog.repositories.MyUsersRepo;
import com.blog.repositories.PostRepository;
import com.blog.service.PostService;
@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private MyUsersRepo usersRepo;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer catId) {
		
		Categories category = categoryRepository.findById(catId).orElseThrow(()->new ReourceNotFoundException("category","category Repository",catId));
		
		MyUsers user = usersRepo.findById(userId).orElseThrow(()->new ReourceNotFoundException("User","User Repository",userId));
		
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setCategory(category);
		post.setUsers(user);
		
	
		return modelMapper.map(postRepository.save(post), PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto,Integer id) {
		
		Post post = postRepository.findById(id).orElseThrow(()->new ReourceNotFoundException("Post","Post Repository",id));
		Post newPost =  modelMapper.map(postDto,Post.class);
		
		post.setCategory(newPost.getCategory());
		post.setContent(newPost.getContent());
		post.setImageName(newPost.getImageName());
		post.setTitle(newPost.getTitle());
		post.setUsers(newPost.getUsers());
		
		
		return modelMapper.map(postRepository.save(post), PostDto.class);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDirn) {
		
		Sort sort;
		if(sortDirn.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}
		else {
			sort = Sort.by(sortBy).descending();
		}
		
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> pagePost = postRepository.findAll(p);
		
		 List<Post>posts =  pagePost.getContent();
		 List<PostDto> postDtos = posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		 
		 PostResponse pr = new PostResponse();
		
		 pr.setContent(postDtos);
		 pr.setPageNumber(pagePost.getNumber());
		 pr.setPageSize(pagePost.getSize());
		 pr.setTotalElement(pagePost.getNumberOfElements());
		 pr.setTotalPage(pagePost.getTotalPages());
		 pr.setLastPage(pagePost.isLast());
		 
		return pr;
	}

	@Override
	public PostDto getPostByID(Integer id) {
		
		Post post = postRepository.findById(id).orElseThrow(()->new ReourceNotFoundException("Post","Post Repository",id));
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public void deletePost(Integer id) {
		postRepository.deleteById(id);
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		MyUsers user = usersRepo.findById(userId).orElseThrow(()->new ReourceNotFoundException("User","User repository",userId));
	 	List<Post>posts = postRepository.findByUsers(user);
	 	List<PostDto> postDtos = posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer catId) {
		Categories category = categoryRepository.findById(catId).orElseThrow(()->new ReourceNotFoundException("category","Category Repository",catId));
		List<Post>posts = postRepository.findByCategory(category);
	 	List<PostDto> postDtos = posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> posts = postRepository.searchByTitle("%" +keyword + "%");
		List<PostDto> postDtos = posts.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
