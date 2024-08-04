package com.blog.security;

import lombok.Data;

@Data
public class JwtAuthRequest {

	String username;
	
	String password;
}
