package com.blog.service;

import java.util.List;

import com.blog.payload.MyUsersDto;

public interface MyUsersService {
	MyUsersDto registerUser(MyUsersDto usersDto);
	MyUsersDto createUser(MyUsersDto userDto);
	MyUsersDto updateUser(MyUsersDto userDto,Integer id);
	MyUsersDto getUserById(Integer id);
	List<MyUsersDto> getAllUsers();
	void deleteUser(Integer id);
}
