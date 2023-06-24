package com.r2s.demo.Repository;

import org.springframework.stereotype.Repository;

import com.r2s.demo.model.Category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository

public interface CategoryRepository extends JpaRepository<Category, Long>{
	Optional<Category> findByName(String name);
	
}
