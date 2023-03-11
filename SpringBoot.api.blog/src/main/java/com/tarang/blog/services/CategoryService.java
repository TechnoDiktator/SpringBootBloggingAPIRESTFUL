package com.tarang.blog.services;


import com.tarang.blog.payloads.CategoryDto;
import java.util.List;

public interface CategoryService {

	
	//create 
	public CategoryDto createCategory(CategoryDto categorydto);
	//update
	public CategoryDto updateCategory(CategoryDto categorydto , Integer categoryid);
	//delete
	public void deleteCategory(Integer categoryid);
	//get single category
	public CategoryDto getCategory(Integer categoryid);
	//get all categories
	public List<CategoryDto> getCategories();
	
	
}
