package com.r2s.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.r2s.demo.model.Address;
@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{
	
}
