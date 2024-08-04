package com.blog.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGenertor implements CommandLineRunner {

	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void run(String... args) throws Exception {

		System.out.println(passwordEncoder.encode("kavya"));
	}

}
