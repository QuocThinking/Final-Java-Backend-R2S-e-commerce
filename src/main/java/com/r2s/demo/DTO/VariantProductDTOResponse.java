package com.r2s.demo.DTO;

import com.r2s.demo.model.VariantProduct;

import lombok.Data;

@Data
public class VariantProductDTOResponse {

	private Long id;
	private String color;
	private int modelYear;
	private String name;
	private double price;
	private String size;
	private Long productId;
	
	public VariantProductDTOResponse(VariantProduct variantProduct) {
		this.id = variantProduct.getId();
		this.color = variantProduct.getColor();
		this.modelYear = variantProduct.getModel_year();
		this.name = variantProduct.getName();
		this.price = variantProduct.getPrice();
		this.size = variantProduct.getSize();
		this.productId = variantProduct.getProduct().getId();
	}
}
