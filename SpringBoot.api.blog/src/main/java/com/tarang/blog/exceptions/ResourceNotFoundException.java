package com.tarang.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	String resourceNmae;
	String fieldName;
	long fieldValue;
	
	//constructor
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(  String.format(resourceName + " not found with : " + resourceName + " " +fieldName + " " +fieldValue ) );
		this.resourceNmae = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
}
