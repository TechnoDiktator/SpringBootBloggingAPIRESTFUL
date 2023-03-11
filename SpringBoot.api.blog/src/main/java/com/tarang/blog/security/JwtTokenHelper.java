package com.tarang.blog.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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
* 
*

* 		1 2 3
* eg   a.b.c   this is the format of the taken
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

@Component
public class JwtTokenHelper {

	//this vill be the time for token validity
	public static final long JWT_TOKEN_VALIDITY = 5*60*60;
	
	
	private String secret = "jwtTokenKey";
	
	
	
	//retreive username from jwt token
	public String GetUserNameFromToken(String token) {
		
		return getClaimFromToken(token , Claims::getSubject);
		
	}
	
	
	//get Expiration date from token
	public Date getExpirationDateFromToken(String token) {
		
		return getClaimFromToken(token , Claims::getExpiration);
	} 
	
	public <T> T getClaimFromToken(String token , Function<Claims , T> claimsResolver) {
		
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
		
	}
	
	//for retreiving any info from token we need the secret key
	private Claims getAllClaimsFromToken(String token) {
		
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	} 
	
	//check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration  = getExpirationDateFromToken(token);
		return expiration.before(new Date());
		
		
	}
	
	//generate new token from user 
	public String generateToken(UserDetails userDetails) {
	
		Map<String  , Object > claims = new HashMap<>();
		
		return doGenerateToken(claims , userDetails.getUsername());
		
	}
	
	
	//while cerating the token
	//1. Define claims of the token, like Issuer , Expiration  , Subject ,and the ID
	//2. Sign the JWT using the HS512 algirithm and secret key
	//3.
	public String doGenerateToken(Map<String  , Object>  claims , String Subject) {
		
		
		return 	Jwts.builder().setClaims(claims).setSubject(Subject).setIssuedAt(new Date(System.currentTimeMillis()))
		.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000)).signWith(SignatureAlgorithm.HS512 , secret).compact();
		
	}
	
	
	
	public boolean validateToken(String token , UserDetails userDetails){
		
		final String userName = GetUserNameFromToken(token);
		return (userName.equals(userDetails.getUsername())&&!isTokenExpired(token));
		
	}
	
	
}
