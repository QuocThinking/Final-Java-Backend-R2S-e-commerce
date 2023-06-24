package com.r2s.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.r2s.demo.model.VariantProduct;

@Repository
public interface VariantProductRepository extends JpaRepository<VariantProduct, Long>{
	List<VariantProduct> findVariantProductByName(String name);
	
	@Modifying
	@Query(nativeQuery = true, value = "select * from  variant_product where name like :name")
	List<VariantProduct> findbyVariantProductByNameSQL( String name);
}
