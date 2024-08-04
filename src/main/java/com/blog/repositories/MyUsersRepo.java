package com.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.MyUsers;

public interface MyUsersRepo extends JpaRepository<MyUsers, Integer> {

	Optional<MyUsers> findByEmail(String email);
}
