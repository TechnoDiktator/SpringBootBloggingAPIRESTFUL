package com.tarang.blog.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tarang.blog.payloads.ApiResponse;
import com.tarang.blog.payloads.CategoryDto;
import com.tarang.blog.services.CategoryService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	
	//create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody  CategoryDto categoryDto){
		
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
		
	}
	
	
	//update
	@PutMapping("{catid}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody  CategoryDto categoryDto , @PathVariable("catid") Integer categoryId){
		
		CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto , categoryId);
		return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.CREATED);
		
	}	
	
	
	
	//delete
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory( @PathVariable("catId") Integer categoryId){
		
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(  "category is deleted successfully"  , false), HttpStatus.OK);	
	}
	
	
	
	//get
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategory( @PathVariable("catId") Integer categoryId){
		
		CategoryDto categorydto=	this.categoryService.getCategory(categoryId);
		return new ResponseEntity<CategoryDto>(categorydto, HttpStatus.OK);	
	}
	
	
	//getall
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getCategory(){
		List<CategoryDto> categorydtos=	this.categoryService.getCategories();
		return  ResponseEntity.ok(categorydtos);	
	}
	
	

}
