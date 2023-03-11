package com.tarang.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	

	private Integer categoryId;
	
	@NotBlank
	private String categoryTitle;
	
	@NotBlank
	@Size(min = 10)
	private String categoryDescription;

}
