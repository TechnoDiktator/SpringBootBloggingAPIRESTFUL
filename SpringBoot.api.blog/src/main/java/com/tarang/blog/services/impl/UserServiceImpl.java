package com.tarang.blog.services.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tarang.blog.exceptions.*;
import com.tarang.blog.config.AppConstants;
import com.tarang.blog.entities.Role;
import com.tarang.blog.entities.User;
import com.tarang.blog.payloads.UserDto;
import com.tarang.blog.repositories.RoleRepo;
import com.tarang.blog.repositories.UserRepo;
import com.tarang.blog.services.UserService;





//this is the implementation class of the userservice interface
//What is @service and @component in Spring?
//@Component is a generic stereotype for any 
//Spring-managed component. @Service annotates classes at the 
//service layer. @Repository annotates classes at the persistence 
//layer, which will act as a database repository



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
public class UserServiceImpl implements UserService {
	
	
	/*
	 Spring @Autowired annotation is used for automatic dependency 
	 injection. Spring framework is built on dependency injection 
	 and we inject the class dependencies through spring bean 
	 configuration file 
	 */
	
	
	@Autowired
	private ModelMapper modelmapper;
	
	//----------------------------------------------------
	@Autowired
	private UserRepo userRepo;   //our userrepo interface already inherits JPArepository
	
	//NOTE that userrepo is an interface so how can its object be created  ???????
	//ANSWER ---> SpringBoot will dynamically create a class during for this interface and it will implement all the abstract
				 //methods of this interface. Also since this UserRepo class inherits from JPA repository so we will get all its method eg- findUSerById , findAll , save
	
	//----------------------------------------------------
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo ;
	
	
	
	
	//this method will convert UserDto object to USer class object 
	private User dtoToUser(UserDto userdto) {
		
		//WE WILL USE MODEMAPPER TO MAP UserDTo object to User class object 
		//this will automatically and intelligently map userdto to user  ----- the fields in both objetcs should be similar
		User user  = this.modelmapper.map(userdto, User.class);
		
		
		
//		User user  =  new User();
//		user.setId(userdto.getId());
//		user.setName(userdto.getName());
//		user.setEmail(userdto.getEmail());
//		user.setAbout(userdto.getAbout());
//		user.setPassword(userdto.getPassword());
	
		
		return user;
		
	}
	
	//this method will convert a user object to UserDto class object 
	public UserDto userToDto(User user) {
		
		UserDto udto = this.modelmapper.map(user, UserDto.class);
	
		
		
//		udto.setId(user.getId());
//		udto.setName(user.getName());
//		udto.setEmail(user.getEmail());
//		udto.setAbout(user.getAbout());
//		udto.setPassword(user.getPassword());
		return udto; 
	
	}
	
	

	@Override
	public UserDto createUser(UserDto userdto) {
		// TODO Auto-generated method stub
		
		
		//first we will convert the userdto object to a user object
		User user  =  this.dtoToUser(userdto);
		
		//then we will save the user to our user table using jpareository save method
		User savedUser = this.userRepo.save(user);
		
		//then we will convert our saveduser object to userdto type and return it
		return this.userToDto(savedUser);
		
	}

	@Override
	public UserDto updateUser(UserDto userdto, Integer usreId) {
		/*
		 * The findById() method is used to retrieves an entity by its 
		 * id and it is available in CrudRepository interface. The CrudRepository 
		 * extends Repository interface. In Spring Data JPA Repository is top-level
		 *  interface in the hierarchy.
		 */
		
		//Finding user by id
		User user = this.userRepo.findById(usreId).orElseThrow(  ()-> new ResourceNotFoundException("User" , "id" , usreId ) );

		
		//updating user dtaa
		user.setName(userdto.getName());
		user.setEmail(userdto.getEmail());
		user.setAbout(userdto.getAbout());
		user.setPassword(userdto.getPassword());
		
		User updateduser = 	this.userRepo.save(user);
		UserDto updateduserdto =  this.userToDto(updateduser);
		
		
		return updateduserdto;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user  =  this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User"  ,  "Id"  , userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> users = this.userRepo.findAll();
		//here we are creating a list of userDto object for all the user list objects
		List<UserDto> userdtos = 	users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return userdtos;
	}
	
	

	@Override
	public void deleteUser(Integer userid) {
		
		//we qill get the user from the db using findById of Jpa repository
		User user = this.userRepo.findById(userid).orElseThrow(()->new ResourceNotFoundException("User", "Id", userid));
		this.userRepo.delete(user);
		
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		// TODO Auto-generated method stub
		
		User user = this.modelmapper.map(userDto, User.class);
		
		//encodimng the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//setting role to NORMAL
			Role role =	this.roleRepo.findById(AppConstants.NORMAL_USER).orElseThrow(()->new ResourceNotFoundException("Role", "roleid", AppConstants.NORMAL_USER));   //502 is the role id for a normal role
			user.getRoles().add(role);
			
			//saving to database
			User newUser = 	this.userRepo.save(user);
			
			return this.modelmapper.map(newUser, UserDto.class);
	
	}
	
	
	
	
	
	
	

	
	
}
