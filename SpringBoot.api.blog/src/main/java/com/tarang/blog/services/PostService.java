package com.tarang.blog.services;

import java.util.List;

import com.tarang.blog.entities.Post;
import com.tarang.blog.payloads.PostDto;
import com.tarang.blog.payloads.PostResponse;

public interface PostService {
	
	//create
	public PostDto createPost(PostDto postDto ,  Integer userId , Integer categoryId);
	
	//update
	public PostDto updatePost(PostDto postDto , Integer postId );
	
	//delete
	public void deletePost(Integer postId);
	

	
	//get single post
	public PostDto getPostById(Integer postId);
	
	//get all posts by category
	public List<PostDto> getPostsByCategory(Integer categoryId);
	
	//get all posts by user 
	public List<PostDto> getPostsByUser(Integer userId);

	//search posts
	public List<PostDto> serachPost(String keyword);
	
	//get all posts
	public PostResponse getAllPosts(Integer pageNumber  , Integer pageSize , String sortBy , String sortDir);


	

}

