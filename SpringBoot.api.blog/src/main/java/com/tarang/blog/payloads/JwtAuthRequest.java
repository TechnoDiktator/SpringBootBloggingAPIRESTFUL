package com.tarang.blog.payloads;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class JwtAuthRequest {
	
	private String username;
	private String password;
	
	

}
