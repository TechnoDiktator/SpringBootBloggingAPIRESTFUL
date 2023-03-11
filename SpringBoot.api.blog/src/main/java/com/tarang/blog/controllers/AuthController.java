package com.tarang.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tarang.blog.exceptions.ApiException;
import com.tarang.blog.payloads.JwtAuthRequest;
import com.tarang.blog.payloads.JwtAuthResponse;
import com.tarang.blog.payloads.UserDto;
import com.tarang.blog.security.JwtTokenHelper;
import com.tarang.blog.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;	
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired     //for authenticating username and password
	private AuthenticationManager authenticationManager;
	
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request ){
	
			
		String username = request.getUsername();
		String password = request.getPassword();
		System.out.println(username + password);
		
		//authenticating the user
		this.authenticate(request.getUsername() , request.getPassword() );
		
		//if authenticated then we can get the userdetails
		
		UserDetails userDetails = 	this.userDetailsService.loadUserByUsername(request.getUsername());
		
		
		
		//then we generate the token for the user
		String generatetoken = 	this.jwtTokenHelper.generateToken(userDetails);
	
		//we create the response onject 
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(generatetoken);
		
		//we pass the response
		return new ResponseEntity<JwtAuthResponse>(response , HttpStatus.OK);
	}

	
	private void authenticate(String username, String password) {
		//get the username and password token
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		try {
			
			//authenticate the token
			System.out.println("trying authentication");
			
			//this will authenticate if our username and password are correct
			this.authenticationManager.authenticate(token);		
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApiException("Invalid Username or Password");
		}
	}
	
	
	
	//FOr registering as new user   we need to make this URL unsecure from springsecurityconfig class
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
		
		UserDto userDto2 = this.userService.registerNewUser(userDto);
		
		return new ResponseEntity<UserDto>(userDto2 , HttpStatus.CREATED);
		
	}
	
	
	
}
