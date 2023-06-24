package com.r2s.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.r2s.demo.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
	Cart findUserById(Long userId);
}
