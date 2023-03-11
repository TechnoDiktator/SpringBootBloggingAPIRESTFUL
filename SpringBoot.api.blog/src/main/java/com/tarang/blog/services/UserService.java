package com.tarang.blog.services;


import com.tarang.blog.payloads.UserDto;

import java.util.*;

public interface  UserService {

	//so we will use the userdto class to  expose user data to the client
	//we will not let the client see the user object directly
	
	public	UserDto registerNewUser(UserDto user);
	public UserDto createUser(UserDto user);
	public UserDto updateUser(UserDto user , Integer usreId );
	public UserDto getUserById(Integer userId);
	public List<UserDto> getAllUsers();
	public void deleteUser(Integer userid);
	
	
}
