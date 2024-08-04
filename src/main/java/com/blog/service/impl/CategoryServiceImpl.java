package com.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Categories;
import com.blog.exceptions.ReourceNotFoundException;
import com.blog.payload.CategoryDto;
import com.blog.repositories.CategoryRepository;
import com.blog.service.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		categoryRepository.save(categoryDtoToCategory(categoryDto));
		return categoryDto;
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer id) {
		Categories category = categoryRepository.findById(id).orElseThrow(() -> new ReourceNotFoundException("category","categoryRepository",id));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setDescription(categoryDto.getDescription());
		categoryRepository.save(category);
		return categoryToCategoryDto(category);
	}

	@Override
	public CategoryDto getSingleCategory(Integer id) {
		Categories category = categoryRepository.findById(id).orElseThrow(() -> new ReourceNotFoundException("category","categoryRepository",id));
		return categoryToCategoryDto(category);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Categories> categories = categoryRepository.findAll();
		
		List<CategoryDto> categoryDtos = categories.stream().map((cat) -> categoryToCategoryDto(cat)).collect(Collectors.toList());
		
		return categoryDtos;
	}

	@Override
	public void deleteCategory(Integer id) {
		categoryRepository.findById(id).orElseThrow(() -> new ReourceNotFoundException("category","categoryRepository",id));
		categoryRepository.deleteById(id);
	}
	
	private CategoryDto categoryToCategoryDto(Categories categories) {
		return modelMapper.map(categories, CategoryDto.class);
	}
	
	private Categories categoryDtoToCategory(CategoryDto categoryDto) {
		return modelMapper.map(categoryDto, Categories.class);
	}

}
