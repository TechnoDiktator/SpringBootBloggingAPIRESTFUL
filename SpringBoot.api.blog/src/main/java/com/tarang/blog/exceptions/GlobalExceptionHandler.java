package com.tarang.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tarang.blog.payloads.ApiResponse;

/*
 * What is controller advice in Spring Boot?
The@ControllerAdvice annotation allows us to consolidate our
 multiple, scattered @ExceptionHandlers from before into a single,
  global error handling component. 
  The actual mechanism is extremely simple but also very flexible: 
  It gives us full control over the body of the response as 
  well as the status code.03-Sept-2022
 * 
 */



//this annotation is used to handle all the exceptions that can occure
@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> 
	resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		
		String message  = ex.getMessage(); 
		ApiResponse apiresp = new ApiResponse(message , false);
		return new ResponseEntity<ApiResponse>(apiresp , HttpStatus.NOT_FOUND);
	
	}
	
	
	
	
	
	//THIS WILL HANDLE ALL THE INVALID ARGUEMENT EXCEPTIONS 
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String , String>> handleMethodArguementNotValidException(MethodArgumentNotValidException ex){
		
		Map<String , String> resp = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error)->{
			
			//each error needs to be typecasted to a fielderror
			String errorfield = ((FieldError)error).getField();
			String message = 	error.getDefaultMessage();
			resp.put(errorfield, message);
			
		});
		return new ResponseEntity<Map<String,String>>(resp , HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> 
	apiExceptionHandler(ApiException ex){
		
		String message  = ex.getMessage(); 
		ApiResponse apiresp = new ApiResponse(message , false);
		return new ResponseEntity<ApiResponse>(apiresp , HttpStatus.BAD_REQUEST);
	
	}
	
	
	
	
	
	
	

}
