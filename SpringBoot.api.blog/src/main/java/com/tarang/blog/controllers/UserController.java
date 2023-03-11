package com.tarang.blog.controllers;

import java.util.*;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tarang.blog.payloads.ApiResponse;
import com.tarang.blog.payloads.UserDto;
import com.tarang.blog.services.UserService;

import javax.validation.Valid;

import java.util.*;



/*
 What is @RestController used for?
RestController: RestController is used for making restful web 
services with the help of the @RestController annotation. This 
annotation is used at the class level and allows the class to handle 
the requests made by the client. Let's understand @RestController 
annotation using an example.26-Nov-2021

What is the use of @RequestMapping annotation?
RequestMapping annotation is used to map web requests onto
 specific handler classes and/or handler methods. @RequestMapping \
 can be applied to the controller class as well as methods.
*
*/






@RestController
@RequestMapping("/api/users")
public class UserController {
	
	
	@Autowired     //this will auowire the  userservice implementation
	private UserService userService;

	
	//POST - create user
	
	@PostMapping("/")                        //the @valid annotation is coming from the spring-boot-starter-validation dependency
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userdto){
		
		UserDto createUserDto = this.userService.createUser(userdto);
		
		
		/*
		What is ResponseEntity in spring boot?
		ResponseEntity represents the whole HTTP response: status code, 
		headers, and body. As a result, we can use it to fully configure 
		the HTTP response. If we want to use it, we have to return it from the
		 endpoint; Spring takes care of the rest. ResponseEntity is a generic type
		 */
		
		return new ResponseEntity<>(createUserDto , HttpStatus.CREATED);
		
	}
	
	
	//PUT - update user      here we will send the userid with the URI
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userdto , @PathVariable("userId") Integer uid){
		
		UserDto updateduserdto = 	this.userService.updateUser(userdto, uid);
		
		//we will send an ok response with the updateduserdto
		return ResponseEntity.ok(updateduserdto);
		
		
	}
	
	
	
	//DELETE - delete user - ONLY ADMIN SHOULD BE ALLOWED TO DO THIS
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer uid ){
		
		this.userService.deleteUser(uid);
		
		//client will get a message user deleted successfully in the response
		return new ResponseEntity<>(new ApiResponse("User Deleted Successfully" ,true) , HttpStatus.OK);
		
		
		
		
	}
	
	
	
	
	//GET - all user get
	//getting all the users
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	
	
	//GET - get user by id
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") Integer uid){
		return ResponseEntity.ok(this.userService.getUserById(uid));
	}
	

}
