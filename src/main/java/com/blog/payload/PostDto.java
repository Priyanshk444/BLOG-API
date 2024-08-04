package com.blog.payload;

import java.util.Date;
import java.util.List;

import com.blog.entities.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	
	private Integer Id;

	private String title;
	
	private String content;
	
	private String imageName;
	
	private Date addedDate;
	
	
	private CategoryDto category;
	
	
	private MyUsersDto users;
	
	private List<CommentDto> comments;
}
