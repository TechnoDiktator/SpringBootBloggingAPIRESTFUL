package com.tarang.blog.controllers;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.StreamUtils;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tarang.blog.config.AppConstants;
import com.tarang.blog.entities.Post;
import com.tarang.blog.payloads.ApiResponse;
import com.tarang.blog.payloads.PostDto;
import com.tarang.blog.payloads.PostResponse;
import com.tarang.blog.services.FileService;
import com.tarang.blog.services.PostService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;
	@Autowired
	private FileService fileservice;
	
	@Value("${project.image}")     //comes from application properties
	private String path;
	
	
	//create 
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto  , 
			@PathVariable("userId") Integer userId , 
			@PathVariable("categoryId") Integer categoryId ){
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);		
		return new ResponseEntity<PostDto>(createPost , HttpStatus.CREATED); 
	}
	
	
	
	
	//get posts by user 
	@GetMapping("/user/{userId}/posts")
	public  ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable("userId")  Integer userId){
		
		List<PostDto> posts = this.postService.getPostsByUser(userId);
	
		return new ResponseEntity<List<PostDto>>(posts , HttpStatus.OK);
		
	}
	
	
	
	//get posts by category
	@GetMapping("/category/{categoryId}/posts")
	public  ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("categoryId")  Integer categoryId){
		
		List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
	
		return new ResponseEntity<List<PostDto>>(posts , HttpStatus.OK);
		
	}
	
	
	//EXTREMELY IMPORTANT CONCEPT USED _______PAGINATION AND SORTING________
	
	//NOTE THAT ALL THE PARAMETRES WE HAVE PASSED BELOW ARE OPTIONAL
	//get all posts      USES PAGINATION
	@GetMapping("/posts")
	public  ResponseEntity<PostResponse> getAllPosts(            // USING THE CONSTANTS WE HAVE DEFINED
			@RequestParam(value = "pageNumber"  , defaultValue = AppConstants.PAGE_NUMBER  , required = false) Integer pageNumber , 
			@RequestParam(value = "pageSize"  , defaultValue = AppConstants.PAGE_SIZE  , required = false) Integer pageSize,
			@RequestParam(value = "sortBy"  , defaultValue = AppConstants.SORT_BY , required = false) String sortby,
			@RequestParam(value = "sortDir" , defaultValue = AppConstants.SORT_DIR , required = false ) String sortDir
			){
		//HERE WE WILL PREPARE A WHOLE POSTRESPONSE CLASS OBJECT WHICH WILL CONTAIN ALL INFO
		//WE HAVE CREATED THIS CLASS IN PAYLOADS PACKAGE
		PostResponse response = this.postService.getAllPosts(pageNumber , pageSize , sortby , sortDir);
		return new ResponseEntity<PostResponse>(response , HttpStatus.OK);
		
	}

	
	
	
	
	
	//get post by postid
	@GetMapping("/posts/{postid}")
	public ResponseEntity<PostDto> getPostById( @PathVariable("postid") Integer postId) {
		
		PostDto pdto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(pdto , HttpStatus.OK);
		
	}
	
	
	//delete post
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost( @PathVariable("postId") Integer postId) {
		
		this.postService.deletePost(postId);
		return new ApiResponse("Post is successfully deleted " , true);
		
	}
	
	
	//update a post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> UpdatePostById( @RequestBody PostDto postdto  ,  @PathVariable("postId") Integer postId) {
		PostDto updatedPost = this.postService.updatePost(postdto, postId);
		return new ResponseEntity<PostDto>(updatedPost , HttpStatus.OK);
	}
	
	@GetMapping("posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle (@PathVariable("keywords") String keyword){
		List<PostDto> searchRes = this.postService.serachPost(keyword);
		return new ResponseEntity<List<PostDto>>(searchRes , HttpStatus.OK);
	}
	
	
	//post image upload 
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId
	)throws IOException{
		
		//first we find whether the post with given id is present and return its postdto object
		PostDto postDto = 	this.postService.getPostById(postId);
		
		String fileName = this.fileservice.uploadImage(path, image);
		
		//earlier the image name was  default.png ...now we will change it to the new name
		postDto.setImagename(fileName);
		PostDto updatedPost = 	this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost ,  HttpStatus.OK);
	}
	
	
	
	//download image of the post
	@GetMapping(value = "/posts/image/{imageName}" ,produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName , 
			HttpServletResponse response
			)throws IOException{
		//path  -  is a class variable look at the beginning 
		InputStream resource = this.fileservice.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		org.springframework.util.StreamUtils.copy(resource, response.getOutputStream());
	}
	
	
	
	
	
	
}
