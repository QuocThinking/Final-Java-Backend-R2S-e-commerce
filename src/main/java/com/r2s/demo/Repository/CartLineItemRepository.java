package com.r2s.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.r2s.demo.model.CartLineItem;

@Repository
public interface CartLineItemRepository extends JpaRepository<CartLineItem, Long>{
	List<CartLineItem> findAllByCartId(Long cartId);
	
	List<CartLineItem> findAllByIsDeleted(boolean isDeleted);

}
