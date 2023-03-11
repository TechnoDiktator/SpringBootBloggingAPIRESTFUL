package com.tarang.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.tarang.blog.security.CustomUserDetailService;
import com.tarang.blog.security.JwtAuthenticationEntryPoint;
import com.tarang.blog.security.JwtAuthenticationFilter;

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







@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter  {


	// the v3/api-doc , v2/api-doc named url in this pulls the swagger json format 
	public static final String[] PUBLIC_URLS = {
			"/api/auth/login","/api/auth/register","/v3/api-docs","/swagger-resources/**",
			"/swagger-ui/**","/webjars/**","/v2/api-docs"
			
	};
	
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		
			
			http.csrf()
			.disable()
			.authorizeHttpRequests().antMatchers("/api/auth/login").permitAll() //this will allow this first page that is login page to run without authorization
			.antMatchers(HttpMethod.GET).permitAll()   //this will allow the anyone to access the GET Type requests 
			.antMatchers("/api/auth/register").permitAll() //this will allow anyone too register withour requiring spring authentication by JWT
			.antMatchers(PUBLIC_URLS).permitAll()  //we can also store the above urls in an array and permit them this way
			.anyRequest()                                                           
			.authenticated()
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);    //setting session management property to ststeless
				
			http.addFilterBefore(this.jwtAuthenticationFilter  , UsernamePasswordAuthenticationFilter.class );
			
	}

	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	
	
	
	

}
