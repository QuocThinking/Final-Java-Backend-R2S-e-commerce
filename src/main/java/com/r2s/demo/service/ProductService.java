package com.r2s.demo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.r2s.demo.Repository.ProductRepository;
import com.r2s.demo.model.Product;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public Page<Product> getAllProductByCategoryId(Long categoryId, Pageable pageable){
		return this.productRepository.findByCategoryId(categoryId, pageable);
	}
	
	public Product findProductById(Long id) {
		return this.productRepository.findById(id).orElse(null);
	}
}
