package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payload.ApiResponse;
import com.blog.payload.CategoryDto;
import com.blog.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryControllers {
	
	@Autowired
	private CategoryService categoryService;

	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid@RequestBody CategoryDto categoryDto) {
		return new ResponseEntity<>(categoryService.createCategory(categoryDto),HttpStatus.CREATED);
	}
	
	@PutMapping("/{categoryID}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable("categoryID") Integer uid){
		return ResponseEntity.ok(categoryService.updateCategory(categoryDto, uid));
	}
	
	@DeleteMapping("/{categoryID}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("categoryID") Integer uid){
		categoryService.deleteCategory(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully", true),HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllUser(){
		return new ResponseEntity<List<CategoryDto>>(categoryService.getAllCategory(),HttpStatus.OK);
	}
	
	@GetMapping("/{categoryID}")
	public ResponseEntity<CategoryDto> getSingleUser(@PathVariable("categoryID") Integer uid){
		return new ResponseEntity<CategoryDto>(categoryService.getSingleCategory(uid),HttpStatus.OK);
	}
}
