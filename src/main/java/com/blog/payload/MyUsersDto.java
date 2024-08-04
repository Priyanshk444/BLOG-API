package com.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyUsersDto {
	
	
	private int id;
	@NotEmpty(message = "Must not be Empty")
	private String name;
	@Email(message = "Must be Valid Email")
	private String email;
	@NotEmpty(message = "Must not be empty")
	private String password;
	@NotEmpty(message = "Must not be empty")
	private String about;

}
