package com.tarang.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;


//HERE WE WILL FETCH THE JWT TOKEn


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	
	//what is the purpose of this method
	//this method will run for each request
	//this method will get the token from the request
	//then it will validate the token
	//if the token is valid then it will authenticate the user
	//after that the request will go to the controller
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {
		
		
		
		//1 get token it will be present in the key named Authorization
		String requestToken =  request.getHeader("Authorization");
		
		System.out.println(" requestToken is " +requestToken );
		
		
		String userName = null;
		String token = null;
		
		
		//requestToken format is like   ---->   Bearer 2352523dsdg...
		//if our token starts with bearer then it is correct
		if(requestToken!=null && requestToken.startsWith("Bearer")) {
			
			//getting the string after the word Bearer that is without bearer
			
			token = requestToken.substring(7);
			System.out.println("token after removing bearer " +token);
			
			try {
				
				//getting username
				userName = 	this.jwtTokenHelper.GetUserNameFromToken(token);
				
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get Jwt Token");
			}catch (ExpiredJwtException e) {
				System.out.println("Jwt token has expired");
			}catch (MalformedJwtException e) {
				System.out.println("Invalid jwt excep ");
			}
			
		}else {
			
			System.out.println("JWT Token does not begin with Bearer");
		}
		
		
		
		//once we get the token we will validate it
		
		//if username is not null and the user has not been authenticated yet
		if(userName!= null  && SecurityContextHolder.getContext().getAuthentication()==null){
			
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
//			String a = 	userDetails.getUsername();
//			String b = 	userDetails.getPassword();
//			System.out.println(a + b);
//			
			
			if(this.jwtTokenHelper.validateToken(token, userDetails)) {	
				//working fine now we 
				//now we will authenticate the user
				
				//AuTHENTICATING
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails , null , userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);	
			}else {
				
				System.out.println("Invalid JWT Token");
				
			}
			
		}else {
			
			System.out.println("username is null or context is not null");
			
		}
		
		
		//from here our request will go forward to the controller
		filterChain.doFilter(request, response);
		
		
	}

	
	
	
}

