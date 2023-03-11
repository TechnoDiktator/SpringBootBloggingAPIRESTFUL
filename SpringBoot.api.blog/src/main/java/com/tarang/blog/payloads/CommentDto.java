package com.tarang.blog.payloads;

import com.tarang.blog.entities.Post;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentDto {
	
	//this will be gebnereted automaticaaly
	private int id;
	
	private String content;	

}
