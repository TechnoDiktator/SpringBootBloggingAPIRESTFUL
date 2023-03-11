package com.tarang.blog.services;
import com.tarang.blog.payloads.*;
public interface CommentService {
	
	
	public CommentDto createComment(CommentDto commentdto , Integer postId);
	void deleteComment(Integer commentId);


}
