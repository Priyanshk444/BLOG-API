package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payload.ApiResponse;
import com.blog.payload.MyUsersDto;
import com.blog.service.MyUsersService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
    private	MyUsersService usersService;
	
	@PostMapping("/")
	public ResponseEntity<MyUsersDto> createUser(@Valid@RequestBody MyUsersDto userDto) {
		return new ResponseEntity<>(usersService.createUser(userDto),HttpStatus.CREATED);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<MyUsersDto> updateUser(@RequestBody MyUsersDto usersDto,@PathVariable("userId") Integer uid){
		return ResponseEntity.ok(usersService.updateUser(usersDto, uid));
	}
	
	@PreAuthorize("hasRole('Admin')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid){
		usersService.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully", true),HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<MyUsersDto>> getAllUser(){
		return new ResponseEntity<List<MyUsersDto>>(usersService.getAllUsers(),HttpStatus.OK);
	}
	
	@GetMapping("/{userid}")
	public ResponseEntity<MyUsersDto> getSingleUser(@PathVariable("userid") Integer uid){
		return new ResponseEntity<MyUsersDto>(usersService.getUserById(uid),HttpStatus.OK);
	}
	
	
	
}
