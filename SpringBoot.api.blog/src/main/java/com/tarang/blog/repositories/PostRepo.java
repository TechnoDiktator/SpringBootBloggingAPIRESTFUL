package com.tarang.blog.repositories;

import java.util.List;


/*
 * JpaRepository is particularly a JPA specific extension for Repository. 
 * It has full API CrudRepository and PagingAndSortingRepository. 
 * So, basically, Jpa Repository contains the APIs for basic CRUD operations, 
 * the APIS for pagination, and the APIs for sorting.
 */

import org.springframework.data.jpa.repository.JpaRepository;

import com.tarang.blog.entities.Category;
import com.tarang.blog.entities.Post;
import com.tarang.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	/*
	
	WHAT THE HELL ARE THESE findBycolumn_name() methods in the JPA repository ?????
	
	JPA finder methods are the most powerful methods, 
	we can create finder methods to select the records from 
	the database without writing SQL queries. Behind the scenes, 
	Data JPA will create SQL queries based on the finder method and execute the query for us.
	
	*/
	
	
	public List<Post> findByUser(User user);   //note that the implementations of these methods are present in the JpaRepository
	public List<Post> findByCategory(Category category );  //note that the implementstaions of these methods are present in the JpaRepository
	
	
	//THIS Will be our search function that will search using the title
	public List<Post> findByTitleContaining(String title);
	

}
