package com.r2s.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.r2s.demo.Repository.UserRepository;
import com.r2s.demo.model.User;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public List<User> findByName(String name) {
		return this.userRepository.findByNameSQL(name);
	}
	
	public List<User> getAlluser(){
		return this.userRepository.findAll();
	}
	
	public User findUserById(long id) {
		return this.userRepository.findById(id).orElse(null);
	}
	public void removeUser(long id) {
		this.userRepository.deleteById(id);
	}
}
