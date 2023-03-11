package com.tarang.blog.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
@Setter
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer categoryId;
	
	@Column(name="title" , length = 100 , nullable = false)
	private String categoryTitle;
	
	
	@Column(name = "description")
	private String categoryDescription;
	
	//every category will have multiple posts
	//spring will automatically look for the category field
	@OneToMany(mappedBy = "category"  , cascade = CascadeType.ALL)   //all cascade operations will turn on
	private List<Post> posts = new ArrayList<>();
	
	
	
	
	
	
}
