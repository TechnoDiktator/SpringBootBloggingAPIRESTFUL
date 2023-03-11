package com.tarang.blog.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

//WE WILL USE JWT
/*
* JSON Web Token is an open industry standard used to share information between two entities,
*  usually a client (like your app's frontend) and a server (your app's backend). They contain JSON objects 
*  which have the information that needs to be shared.24-Mar-2022
*

* It is stateless so it follows restful api requirement because it is not stored on the server side
* helps in communivcation of client and server securely
* 
* a token has three parts:  1 header(For algo type)    2 Payload(Information about claims basically users data)    3 Signature(encoded header + encoded payload + key


* 	   1   2   3
* eg   a . b . c   this is the format of the taken
* 
*                A                                                B                                                                C
* eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
* This token is used to access the URLs (APIs)  and it is present on the client side
* 

STEPS TO IMPLEMENT JWT TOKEN IN SPRINGBOOT APP
1. Add JWT Dependency
2.Create a class thet serves as the entrypoint ->   Create JWTAuthenticationEntryPoints implements AuthenticationEntryPoint
3.Create JWT TokenHelper 
4.Create another calss JwtAuthenticationFilter extends OnceRequestFilter
	.get token from request
	.validate the token
	.get user from token
	.load the user associated with token
	.set spring security
5. Configure JwtAuthResponse
6. Configure JWT in Spring Security config
7. Create login api to return token
8. Test the application
*/





//THIS AUTHENTICATION FAILS THEN THIS WILL AUTOMATICALLY RUN
@Component
public class JwtAuthenticationEntryPoint   implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
				
		
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED , "Access Denied");
		
		
		
		
	}

	
	
	
}
