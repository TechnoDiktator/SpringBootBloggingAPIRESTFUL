package com.tarang.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tarang.blog.entities.User;
import com.tarang.blog.exceptions.ResourceNotFoundException;
import com.tarang.blog.repositories.UserRepo;


@Service
public class CustomUserDetailService implements UserDetailsService {

	
	@Autowired
	private UserRepo userRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub

		User user = this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User", " email : " +username  , 0));	
		
		if(user== null) {
			System.out.println("User is null");
		}
	
		
		//loading user from database by username
		//we have assumed that the email is the username
		return user;
		
	}
}
