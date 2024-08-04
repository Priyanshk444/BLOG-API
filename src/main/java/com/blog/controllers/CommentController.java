package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payload.ApiResponse;
import com.blog.payload.CommentDto;
import com.blog.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
	

	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/comment")
    public CommentDto postComment(@RequestBody CommentDto commDto,@PathVariable Integer postId) {
    	
    	
    	return commentService.postComment(commDto,postId);
         
    }
	
	@DeleteMapping("/comment/{commentId}")
	public ApiResponse deleteComment(@PathVariable Integer commentId) {
		commentService.delete(commentId);
		
		return new ApiResponse("comment deleted successfully", true);
	}

}
