package com.r2s.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.r2s.demo.Repository.VariantProductRepository;
import com.r2s.demo.model.VariantProduct;

@Service
public class VariantProductService {
	
	@Autowired private VariantProductRepository variantProductRepository;
	// tra ve doi tuong theo variantid
//	public VariantProduct getVariantProductById(Long variantId) {
//		Optional<VariantProduct> vaOptional = variantProductRepository.findById(variantId);
//		if(vaOptional.isPresent()) {
//			return vaOptional.get();
//		}
//		return null;
//	}
//	public List<VariantProduct> getVariantProductByName(String name){
//		return variantProductRepository.findVariantProductByName(name);
//	}
	
	public List<VariantProduct> getAllVariantProduct(){
		return this.variantProductRepository.findAll();
	}
	
	public List<VariantProduct> findByName( String name){
		return this.variantProductRepository.findbyVariantProductByNameSQL(name);
	}
}
