package com.tarang.blog.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import io.jsonwebtoken.lang.Collections;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	//when we are using swagger and we want to use JWT in our apis we have to do all this given below
	
	
	
	
	public static final String AUTHORIZATION_HEADER="Authorization";
	
	//1
	private ApiKey apiKeys() {
		
						//nameoftoken     keyname         pass as
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}
	
	
	//2
	private List<SecurityContext> securityContext(){
		return Arrays.asList(	SecurityContext.builder().securityReferences(sref()).build()  );
	}
	
	
	//3
	private List<SecurityReference> sref(){		
		AuthorizationScope scope =  new AuthorizationScope("global", "accessEverything");
														//putting scope at 0th position in the array
		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] {scope}) ) ;
	}
	
	
	//4
	@Bean
	public Docket api() {
		//this will get us all the info that we want to display on 
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getInfo())
				.securityContexts(securityContext())
				.securitySchemes(Arrays.asList(apiKeys()))
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

	//5    this is for displaying our info on the api page in swagger
	private ApiInfo getInfo() {
		
		
		return new  ApiInfo("Backend Project : blogging api", "This project is developed by Tarang rastogi",
				"1.0", "terms of service", new Contact("Tarang Rastogi", "https://github.com/TechnoDiktator", "rastogitarang5@gmail.com"), 
				"Liscence", null, java.util.Collections.emptyList());
	}
	
	
	
	
}
