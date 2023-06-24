package com.r2s.demo.DTO;

import com.r2s.demo.model.Category;

import lombok.Data;

@Data
public class CategoryDTOResponse {
	private Long id;
	private String name;
	
	public CategoryDTOResponse (Category category) {
		this.id = category.getId();
		this.name = category.getName();
	}
}
