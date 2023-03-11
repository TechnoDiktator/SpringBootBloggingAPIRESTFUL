package com.tarang.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tarang.blog.entities.Category;

/*
 * JpaRepository is particularly a JPA specific extension for Repository. 
 * It has full API CrudRepository and PagingAndSortingRepository. 
 * So, basically, Jpa Repository contains the APIs for basic CRUD operations, 
 * the APIS for pagination, and the APIs for sorting.
 */


public interface CategoryRepo extends JpaRepository<Category, Integer> {
	

}
