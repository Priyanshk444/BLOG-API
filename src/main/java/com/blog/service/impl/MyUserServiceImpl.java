package com.blog.service.impl;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.entities.MyUsers;
import com.blog.entities.Role;
import com.blog.payload.MyUsersDto;
import com.blog.repositories.MyUsersRepo;
import com.blog.repositories.RoleRepository;
import com.blog.service.MyUsersService;
import com.blog.exceptions.*;
@Service
public class MyUserServiceImpl implements MyUsersService {
	
	@Autowired
	private MyUsersRepo usersRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public MyUsersDto createUser(MyUsersDto userDto) {
		usersRepo.save(userDtoToUser(userDto));
		return userDto;
	}

	@Override
	public MyUsersDto updateUser(MyUsersDto userDto, Integer id) {
		
		MyUsers users = usersRepo.findById(id).orElseThrow(()->new ReourceNotFoundException("user","userRepo",id));
		
		users.setAbout(userDto.getAbout());
		users.setEmail(userDto.getEmail());
		users.setName(userDto.getName());
		users.setPassword(userDto.getPassword());
		
		usersRepo.save(users);
		
		return userDto;
	}

	@Override
	public MyUsersDto getUserById(Integer id) {
		
		MyUsers user = usersRepo.findById(id).orElseThrow(()-> new ReourceNotFoundException("user", "userRepo", id));
		
		
		return userToDto(user);
	}

	@Override
	public List<MyUsersDto> getAllUsers() {
		
		List<MyUsers> users  = usersRepo.findAll();
		
		List<MyUsersDto> userDtos = users.stream().map(user -> userToDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer id) {
		
		MyUsers user = usersRepo.findById(id).orElseThrow(()-> new ReourceNotFoundException("user", "userRepo", id));
		
		usersRepo.delete(user);

	}
	
	public MyUsers userDtoToUser(MyUsersDto usersDto) {
//		MyUsers user = new MyUsers();
//		user.setId(usersDto.getId());
//		user.setName(usersDto.getName());
//		user.setEmail(usersDto.getEmail());
//		user.setPassword(usersDto.getPassword());
//		user.setAbout(usersDto.getAbout());
		return modelMapper.map(usersDto, MyUsers.class);
	}

	public MyUsersDto userToDto(MyUsers user) {
//		MyUsersDto usersDto = new MyUsersDto();
//		usersDto.setId(user.getId());
//		usersDto.setName(user.getName());
//		usersDto.setEmail(user.getEmail());
//		usersDto.setPassword(user.getPassword());
//		usersDto.setAbout(user.getAbout());
		return modelMapper.map(user, MyUsersDto.class);
	}

	@Override
	public MyUsersDto registerUser(MyUsersDto usersDto) {
		MyUsers user = modelMapper.map(usersDto, MyUsers.class);
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		Role role = roleRepository.findById(2).get();
		
		
		user.getRoles().add(role);
		MyUsers users = usersRepo.save(user);
		return modelMapper.map(users, MyUsersDto.class);
	}
}
