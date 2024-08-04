package com.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.Categories;

public interface CategoryRepository extends JpaRepository<Categories,Integer> {

}
