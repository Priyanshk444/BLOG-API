package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.exceptions.ApiExcepton;
import com.blog.payload.MyUsersDto;
import com.blog.security.JwtAuthRequest;
import com.blog.security.JwtAuthResponse;
import com.blog.security.JwtHelper;
import com.blog.service.MyUsersService;


@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
	private MyUsersService usersService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/register")
	public ResponseEntity<MyUsersDto> registerUser(@RequestBody MyUsersDto usersDto){
		MyUsersDto registerUser = usersService.registerUser(usersDto);
		return new ResponseEntity<MyUsersDto>(registerUser,HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> postMethodName(@RequestBody JwtAuthRequest request) throws Exception {
		
		this.authenticate(request.getUsername(),request.getPassword());
		
		UserDetails userDetail = this.userDetailsService.loadUserByUsername(request.getUsername());
		
		String token = this.jwtHelper.generateToken(userDetail);
		
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		
		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		
		try {
			this.authenticationManager.authenticate(authenticationToken);
		}catch(BadCredentialsException e) {
			System.out.println("Bad details!!");
			throw new ApiExcepton("Invalid username and password"); 
		}
	}
	
}
