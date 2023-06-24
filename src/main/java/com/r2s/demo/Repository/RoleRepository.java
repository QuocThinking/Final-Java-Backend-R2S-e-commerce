package com.r2s.demo.Repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.r2s.demo.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	Set<Role> findByName(String name);
}
