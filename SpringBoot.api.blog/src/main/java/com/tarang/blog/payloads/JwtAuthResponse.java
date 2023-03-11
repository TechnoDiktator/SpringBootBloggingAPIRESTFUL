package com.tarang.blog.payloads;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data    //this automatically makes setters and getters
public class JwtAuthResponse {
	
	private String token;
	

}
