package com.blog.service;

import java.util.List;

import com.blog.payload.CategoryDto;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);
	
	CategoryDto updateCategory(CategoryDto categoryDto,Integer id);
	
	CategoryDto getSingleCategory(Integer id);
	
	List<CategoryDto> getAllCategory();
	
	void deleteCategory(Integer id);
}
