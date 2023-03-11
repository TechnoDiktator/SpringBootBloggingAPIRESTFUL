package com.tarang.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tarang.blog.entities.Category;
import com.tarang.blog.exceptions.ResourceNotFoundException;
import com.tarang.blog.payloads.CategoryDto;
import com.tarang.blog.repositories.CategoryRepo;
import com.tarang.blog.services.CategoryService;

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
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	//NOTE that categoryrepo is an interface so how can its object be created  ???????
		//ANSWER ---> SpringBoot will dynamically create a class during for this interface and it will implement all the abstract
					 //methods of this interface. Also since this CategoryRepo class inherits from JPA repository so we will get all its method eg- findUSerById , findAll , save
		
		//----------------------------------------------------
		
		
	
	
	
	@Override
	public CategoryDto createCategory(CategoryDto categorydto) {
		
		//USING MODEL MAPPER INSTEAD OF SETTERS AND GETTERS
		Category cat = this.modelMapper.map(categorydto , Category.class);
		
		//saving the cat object to the DB
		Category addedCategory = this.categoryRepo.save(cat);
		
		//returning the CategoryDto object corresponding to add object
		return this.modelMapper.map(addedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categorydto, Integer categoryid) {
		
		Category cat =   this.categoryRepo.findById(categoryid).orElseThrow(()->new ResourceNotFoundException("Category", "Category id", categoryid));
		
		//updating the cat object
		cat.setCategoryTitle(categorydto.getCategoryTitle());
		cat.setCategoryDescription(categorydto.getCategoryDescription());
		Category updatedCat = categoryRepo.save(cat);
	
		Category updatedcat = this.categoryRepo.save(cat);
		
		//mapping the updated category object to a categorydto object and returning it to client
		return this.modelMapper.map(updatedcat, CategoryDto.class);
	
	}

	@Override
	public void deleteCategory(Integer categoryid) {
		// TODO Auto-generated method stub
		
		Category cat = this.categoryRepo.findById(categoryid).orElseThrow(()-> new ResourceNotFoundException("category", "category id", categoryid));
		this.categoryRepo.delete(cat);
		
		
	}

	
	
	@Override
	public CategoryDto getCategory(Integer categoryid) {
		// TODO Auto-generated method stub
		
		Category cat =  this.categoryRepo.findById(categoryid).orElseThrow(()-> new ResourceNotFoundException("Category", "category id", categoryid));
		return this.modelMapper.map(cat, CategoryDto.class);
		
	}

	@Override
	public List<CategoryDto> getCategories() {
		// TODO Auto-generated method stub
		List<Category> categories  = this.categoryRepo.findAll();
		
		//here we streamed each and every category in the list and mapped it to a categoryDTo object and put it into another list
		List<CategoryDto> catdtos = 	categories.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class) ).collect(Collectors.toList());
		
		return catdtos;
	}

	
	
	
}
