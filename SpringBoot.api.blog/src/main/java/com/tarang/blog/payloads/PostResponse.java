package com.tarang.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//THIS CLASS IS BEING USED TO SHOW THE DETAILS OF tHE PAGE WE RECEIVED WHEN WE RAN THE getAllPosts method
//the getallposts method was using pagination


@NoArgsConstructor
@Getter
@Setter
public class PostResponse {
	
	private List<PostDto> content;
	private int pageNumnber;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean lastPage;
	
	
	
	
	
	

}
