package com.tarang.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tarang.blog.entities.Comment;
import com.tarang.blog.payloads.ApiResponse;
import com.tarang.blog.payloads.CommentDto;
import com.tarang.blog.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentdto , @PathVariable("postId")  Integer postId){
		
		CommentDto createdcomment=	this.commentService.createComment(commentdto, postId);
		
		return new ResponseEntity<CommentDto>(createdcomment , HttpStatus.CREATED);
		
		
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment( @PathVariable("commentId")  Integer commentId){
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully", true) , HttpStatus.OK);
	}	
	

}
