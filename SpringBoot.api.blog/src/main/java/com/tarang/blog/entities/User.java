package com.tarang.blog.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.jsf.FacesContextUtils;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.AssertFalse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 
 @Entity annotation defines that a 
 class can be mapped to a table. 
 And that is it, it is just a marker, 
 like for example Serializable interface
 
 The @Table annotation allows you to specify the 
 details of the table that will be used to persist the 
 entity in the database. The @Table annotation provides 
 four attributes, allowing you to override the name of the table, 
 its catalog, and its schema, 
 and enforce unique constraints on columns in the table.
 
 
 
  */


@Entity
@Table(name="users")
@NoArgsConstructor
@Getter
@Setter                      //User Details is a spring security package class
public class User implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	
	//you can change the column name by using @Column annotation...and also set its various properties
	@Column(name = "user_name" , nullable =false , length = 100)
	private String name;
	
	private String email;
	
	private String password;
	
	private String about;
	
	//every user will have multiple posts
	//spring will automatically look for the user field in the 
	@OneToMany(mappedBy = "user"  , cascade = CascadeType.ALL , fetch = FetchType.EAGER)   //all cascade operations will turn on
	private List<Post> posts = new ArrayList<>();
	


	
	//manny roles can be followed by a single user and many users can have the same role
	@ManyToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
	@JoinTable(name="user_role" , joinColumns = @JoinColumn(name = "user" , 
	referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name = "role" , referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<>();


	


	@Override    //new to convert role objects to granted authority objects
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		
		List<SimpleGrantedAuthority> authorities = 	this.roles.stream().map((role)->new SimpleGrantedAuthority(role.getName() )).collect(Collectors.toList()) ;
		return null;
	}




	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}




	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}




	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}




	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}




	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
