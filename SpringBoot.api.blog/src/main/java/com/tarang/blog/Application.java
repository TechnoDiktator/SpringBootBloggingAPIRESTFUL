package com.tarang.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tarang.blog.config.AppConstants;
import com.tarang.blog.entities.Role;
import com.tarang.blog.repositories.RoleRepo;

@SpringBootApplication
public class Application implements CommandLineRunner {

	//@Autowired
	//private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
			
	}
	
	
	@Bean
	public ModelMapper  modelMapper() {
		return new ModelMapper();
	}


	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		//System.out.println(this.passwordEncoder.encode("abc"));
		
		
		try {
			
			Role role1 = new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_NORMAL");
			
			Role role2 = new Role();
			role2.setId(AppConstants.ADMIN_USER);
			role2.setName("ROLE_ADMIN");
			 
			//If our application os running for the forst time then we created two role types and saved them to the database
			List<Role> roles = 	List.of(role1 , role2);
			List<Role> save  = this.roleRepo.saveAll(roles);
			
			
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			
			//if role already exists
			e.printStackTrace();
		
		}
		
		
		
		
	}
	
	
	
	
	

}
