package com.r2s.demo.DTO;

import org.springframework.util.ObjectUtils;

import com.r2s.demo.model.Category;
import com.r2s.demo.model.Product;

import lombok.Data;

@Data
public class ProductDTOResponse {

	private Long id;

	private String name;

	private Long caterogyId;

	public ProductDTOResponse(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		if (!ObjectUtils.isEmpty(product.getCategory())) {
			Category category = product.getCategory();
			this.caterogyId = category.getId();
		}
	}
}
