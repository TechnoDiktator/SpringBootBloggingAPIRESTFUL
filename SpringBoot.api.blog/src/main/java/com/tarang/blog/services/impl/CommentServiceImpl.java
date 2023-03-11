package com.tarang.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tarang.blog.entities.Comment;
import com.tarang.blog.entities.Post;
import com.tarang.blog.exceptions.ResourceNotFoundException;
import com.tarang.blog.payloads.CommentDto;
import com.tarang.blog.repositories.CommentRepo;
import com.tarang.blog.repositories.PostRepo;
import com.tarang.blog.services.CommentService;





@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	
	@Override
	public CommentDto createComment(CommentDto commentdto, Integer postId) {
		
		Post post  =  this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId));
		
		Comment comment = 	this.modelMapper.map(commentdto, Comment.class);
		
		comment.setPost(post);
		
		Comment savedComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
		
		
	}

	@Override
	public void deleteComment(Integer commentId) {
		
		Comment com  = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Commenty", "comment id", commentId));
		this.commentRepo.delete(com);
	}

	
	
}
