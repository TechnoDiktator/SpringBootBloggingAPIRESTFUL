package com.tarang.blog.repositories;

import java.util.Optional;

/*
 * JpaRepository is particularly a JPA specific extension for Repository. 
 * It has full API CrudRepository and PagingAndSortingRepository. 
 * So, basically, Jpa Repository contains the APIs for basic CRUD operations, 
 * the APIS for pagination, and the APIs for sorting.
 */


import org.springframework.data.jpa.repository.JpaRepository;

import com.tarang.blog.entities.User;



//JPA REPOSITORY
/*
 Is JpaRepository a class or interface?
The Java Persistence API (JPA) is the standard way of persisting 
java objects into relational databases. 
The central interface in the Spring Data repository abstraction is 
Repository and it takes the domain class to manage as well as the id 
type of the domain class as type arguments.
 
 */


public interface UserRepo extends JpaRepository<User, Integer> {

	/*What are the default methods in JPA Repository?
			Default available methods

			CrudRepository and PagingAndSortingRepository offer (Parent classes)
			default methods such as: findAll, findAllById, findById, 
			deleteAll, deleteById, save, saveAll
			
	*/
	
	
	public Optional<User> findByEmail(String email);
	
	
}
