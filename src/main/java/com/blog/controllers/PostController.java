 package com.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.entities.Comment;
import com.blog.payload.ApiResponse;
import com.blog.payload.CommentDto;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.service.CommentService;
import com.blog.service.ImageService;
import com.blog.service.PostService;

import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private ImageService imageService;
	
	
	@Value("project.image")
	private String path;
	
	@PostMapping("/user/{userId}/category/{catId}")
	public ResponseEntity<PostDto> createPost(
			@RequestBody PostDto postDto,
			@PathVariable("userId") Integer uid,
			@PathVariable("catId") Integer cid
			) {
		
		return new ResponseEntity<PostDto>( postService.createPost(postDto, uid, cid),HttpStatus.CREATED);
		
	}
	
	@GetMapping("/user/{userId}/post")
	public ResponseEntity<List<PostDto>> getUserPosts(@PathVariable("userId") Integer uid){
		return new ResponseEntity<>(postService.getPostByUser(uid),HttpStatus.OK);
	}
	
	@GetMapping("/category/{catId}/post")
	public ResponseEntity<List<PostDto>> getCategoryPosts(@PathVariable("catId") Integer cid){
		return new ResponseEntity<>(postService.getPostByCategory(cid),HttpStatus.OK);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(name = "pageNumber",defaultValue = "1",required = false) Integer pageNumber,
			@RequestParam(name = "pageSize",defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(name = "sort",defaultValue = "id",required = false) String sortBy,
			@RequestParam(name = "sortdirn",defaultValue = "asc",required = false) String sortDirn
			) {
		return new ResponseEntity<PostResponse>(postService.getAllPost(pageNumber,pageSize,sortBy,sortDirn),HttpStatus.OK);
	}
	
	@GetMapping("/post/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("id") Integer id) {
		return new ResponseEntity<PostDto>(postService.getPostByID(id),HttpStatus.OK);
	}
	
	@DeleteMapping("/post/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("id") Integer id){
		postService.deletePost(id);
		return new ResponseEntity<>(new ApiResponse("Post deleted successfully",true),HttpStatus.OK);
	}
	
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@PathVariable("postId") Integer id,@RequestBody PostDto postDto){
		return new ResponseEntity<PostDto>(postService.updatePost(postDto, id),HttpStatus.OK);
	}
	
	@GetMapping("/post/search/{title}")
	public ResponseEntity<List<PostDto>> getMethodName(@RequestParam("title") String keyword) {
		return new ResponseEntity<List<PostDto>>(postService.searchPost(keyword),HttpStatus.OK);
	}
	
	
	@PostMapping("/post/image/{postId}")
	public ResponseEntity<PostDto> postMethodName(@RequestParam("image") MultipartFile multipartFile,@PathVariable Integer postId) throws IOException {
		
		PostDto postDto = postService.getPostByID(postId);
		
		String filename = imageService.uploadFile(path, multipartFile);
		postDto.setImageName(filename);
		PostDto postDto2 = postService.updatePost(postDto, postId);
		
		
		return new ResponseEntity<PostDto>(postDto2,HttpStatus.OK);
	}
	
	
	//serve files
    @GetMapping(value = "/images/{filename}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadFile(@PathVariable("filename") String filename,HttpServletResponse httpServletResponse) throws IOException{


        InputStream resource = this.imageService.getResource(path, filename);
        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, httpServletResponse.getOutputStream());
        
    }
    
    
    
	
	
}
