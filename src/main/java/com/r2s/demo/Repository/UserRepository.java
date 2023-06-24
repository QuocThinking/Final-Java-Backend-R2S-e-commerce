package com.r2s.demo.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.r2s.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByName(String name);
	
	@Modifying
	@Query(nativeQuery = true, value = "select * from user where username like :name")
	List<User> findByNameSQL(String name);
}
