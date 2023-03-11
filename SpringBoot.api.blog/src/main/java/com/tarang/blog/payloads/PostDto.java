package com.tarang.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.tarang.blog.entities.Category;
import com.tarang.blog.entities.Comment;
import com.tarang.blog.entities.User;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
	
	private int postId;
	
	@NotBlank
	private String title;
	
	@NotBlank
	@Size(min = 1  ,message = "Post content must contain atleast one charater")
	private String content;
	
	
	private String imagename;
	
	
	private Date addedDate;
	
	
	private CategoryDto category;
	
	private UserDto user;
	
	
	private Set<CommentDto> comments = new HashSet<>();
	
}
