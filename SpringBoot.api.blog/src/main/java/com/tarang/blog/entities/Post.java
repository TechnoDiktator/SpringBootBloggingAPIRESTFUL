package com.tarang.blog.entities;



import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	
	@Column(name = "post_title" , length = 100, nullable = false)
	private String title;
	
	@Column(length = 10000)
	private String content;
	
	
	private String imagename;
	
	
	private Date addedDate;
	
	
	//now we also need user id  of the user who posted this post 
	//amd also the category id of this post so that it belogs to a specific category
	
	
	//many posts are present in the same category
	@ManyToOne
	@JoinColumn(name = "category_id") //this will change the name of the column from--- Category_category_id   to   category_id 
	private Category category;
	
	
	//many posts are made by one useer
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy = "post" , cascade = CascadeType.ALL )
	private Set<Comment> comments = new HashSet<>();
	
	
	

}
