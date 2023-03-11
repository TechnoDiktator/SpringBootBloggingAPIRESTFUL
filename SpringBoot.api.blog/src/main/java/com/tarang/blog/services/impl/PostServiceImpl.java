package com.tarang.blog.services.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tarang.blog.entities.Category;
import com.tarang.blog.entities.Post;
import com.tarang.blog.entities.User;
import com.tarang.blog.exceptions.ResourceNotFoundException;
import com.tarang.blog.payloads.CategoryDto;
import com.tarang.blog.payloads.PostDto;
import com.tarang.blog.payloads.PostResponse;
import com.tarang.blog.repositories.CategoryRepo;
import com.tarang.blog.repositories.PostRepo;
import com.tarang.blog.repositories.UserRepo;
import com.tarang.blog.services.PostService;


//VERY VERY IMPORTANT   -> WHY ARE WE AUTOWIRING INTERFACES AND NOT THEIR IMPLLEMENTATIONS

/*



Q1
How does spring know which polymorphic type to use.

As long as there is only a single implementation of the interface 
and that implementation is annotated with @Component with Spring's 
component scan enabled, Spring framework can find out the (interface, implementation) pair. 
If component scan is not enabled, then you have to define the bean explicitly 
in your application-config.xml (or equivalent spring configuration file).



Q2
Do I need @Qualifier or @Resource?

Once you have more than one implementation, then you need to qualify each of them and 
during auto-wiring, you would need to use the @Qualifier annotation to inject the right 
implementation, along with @Autowired annotation. 
If you are using @Resource (J2EE semantics), then you should specify the bean name using the name attribute of this annotation.




Q3
Why do we autowire the interface and not the implemented class?

Firstly, it is always a good practice to code to interfaces in general. 
Secondly, in case of spring, you can inject any implementation at runtime. 
A typical use case is to inject mock implementation during testing stage.


*/





@Service
public class PostServiceImpl implements PostService {

	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;   //to get the user od fthe post
	
	@Autowired                   //to get the category of the post
	private CategoryRepo categoryRepo;
	
	
	@Override
	public PostDto createPost(PostDto postDto  , Integer userId , Integer categoryId ) {
		
		//FINDING THE USER OF THE POST BY USERID
		User user =  this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","User id", userId));
		
		//FINDING THE CATEGORY OBJECT OF THE POST BY CATEGORY ID
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "category id", categoryId));
		
		
		//CREATING POST OBJECT
		//so when we mapped the postDto object to post since our postDto does not contain some
		//of the fields that are part of the post class we will implement them		
		Post post =  this.modelMapper.map(postDto, Post.class);
		
		//SETTING THE REMAINING FEILDS
		post.setImagename("defatult.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		//SAVING THE POST IN DB
		Post savedPost = this.postRepo.save(post);
		
		
		return this.modelMapper.map(savedPost, PostDto.class);
	
	}
	
	
	
	
	
	

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		// TODO Auto-generated method stub
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImagename(postDto.getImagename());
	
		Post updatedPost = 	this.postRepo.save(post);
		
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	
	
	
	
	
	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId));
		
		this.postRepo.delete(post);
		
		
	}

	
	
	@Override
	public PostDto getPostById(Integer postId) {
		//GETTINg THE POST FROM THE DB
		Post post = this.postRepo.findById(postId).orElseThrow(()->  new ResourceNotFoundException("Post", "post id", postId)  );
		return this.modelMapper.map(post, PostDto.class);
	}

	
	

	
	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		
		//GETTING THE CATEGORY OBJECT FROM DB
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "category_id", categoryId));
		
		//FINDING ALL THE POSTS FROM THIS CATEGORY
		List<Post> posts = this.postRepo.findByCategory(category);
		
		//STORING THE LIST OF POSTS WE RECEIVED FROM THIS CATEGOIRY INTO  A POSTDTO LIST TO PASS IT INTO RESPONSE WHEN WE MAKE THE CONTROLLER
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList())  ;
		
		return postDtos;
	}

	
	
	
	
	
	
	
	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		//GETTING THE CORRESPONDING USER OBJECT FROM THE DB
		User user  =  this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "UserId", userId));
		
		//FINDING ALL THE POSTS FROM THIS USER
		List<Post> posts = this.postRepo.findByUser(user);
		
		//STORING THE LIST OF POSTS WE RECEIVED FROM THIS USER INTO  A POSTDTO LIST TO PASS IT INTO RESPONSE WHEN WE MAKE THE CONTROLLER
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList())  ;
		
		return postDtos;		
	}

	
	

	//USING PAGING IN THIS FUNCTION
	@Override
	public PostResponse getAllPosts(Integer pageNumber  , Integer pageSize , String sortBy ,String sortDir) {
		Sort sort  = null;
		
		//TO SORT BY EITHER ASCENDING OR DESCENDING
		if(sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
			
		}else {
			sort = Sort.by(sortBy).descending();
			
		}
		
		//HERE WE WILL USE THE PAGING AND SORTING CAPABILITY OF THE JPA REPOSITORY
		Pageable p  = PageRequest.of(pageNumber, pageSize , sort);
		
		//WE RE Returning a page of posts
		Page<Post> pagePosts = this.postRepo.findAll(p);
		List<Post> allPostsOnPage = pagePosts.getContent();
		List<PostDto> postDtos = allPostsOnPage.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList())  ;

		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumnber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());
		return postResponse;
	}
	
	
	
	
	
	
	@Override
	public List<PostDto> serachPost(String keyword) {
		
		
		List<Post> posts =  this.postRepo.findByTitleContaining(keyword);
		System.out.println(posts.size());
		
		
		List<PostDto> postDtoList = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtoList;
	}

	
	
	
	
	
}
