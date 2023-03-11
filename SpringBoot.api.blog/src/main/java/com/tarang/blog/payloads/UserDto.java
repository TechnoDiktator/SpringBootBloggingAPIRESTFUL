package com.tarang.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.tarang.blog.entities.Role;

//LOMBOK - 

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	
	private int id;
	
	
	//all the below validations are coming from the springboot starter validation dependency
	
	@NotNull
	@Size(min = 1  , max = 60 , message = "user name must be atleast 1 character long")
	private String name;
	
	@Email(message = "Email must be valid!!")
	private String email;
	
	@NotNull
	@Size(min = 3 , max = 20 ,   message = "password must be atleast 3 characters long and less than 20 characters")
	private String password;
	
	
	@NotNull
	@Size(min = 1 , message = "about cannot be left empty")
	private String about;
	
	
	private Set<RolesDto> roles = new HashSet<>();

	
	
	//so we will use the userdto class to  expose user data to the client
	
}
