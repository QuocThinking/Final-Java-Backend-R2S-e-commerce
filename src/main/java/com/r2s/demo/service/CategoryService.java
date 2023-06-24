package com.r2s.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.r2s.demo.Repository.CategoryRepository;
import com.r2s.demo.model.Category;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> getAllCategory(){
		return this.categoryRepository.findAll();
	}
	
//	public List<Category> getAllCategory(){
//		return this.categoryRepository.findAll();
//	}
	
	
	
}
