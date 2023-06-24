package com.r2s.demo.Repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.r2s.demo.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
}
