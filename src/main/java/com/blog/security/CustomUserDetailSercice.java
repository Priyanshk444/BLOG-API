package com.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import com.blog.exceptions.ReourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.entities.MyUsers;
import com.blog.repositories.MyUsersRepo;
@Service
public class CustomUserDetailSercice implements UserDetailsService {

	@Autowired
	private MyUsersRepo myUsersRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		MyUsers user = myUsersRepo.findByEmail(username).orElseThrow(()-> new ReourceNotFoundException("user","Email:" + username,0));
		
		return user;
	}

}
