package com.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.entities.Categories;
import com.blog.entities.MyUsers;
import com.blog.entities.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
	
	List<Post> findByUsers(MyUsers myUsers);
	
	List<Post> findByCategory(Categories category);
	
	@Query("select p from Post p where p.title like :key")
	List<Post> searchByTitle(@Param("key") String tiltle);
}
