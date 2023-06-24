package com.r2s.demo.DTO;

import java.util.Map;

import com.r2s.demo.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTORequest {

	private Long id;
	private String email;
	private String phone;
	private String fullname;
	private String name;
	private String password;
	
	public UserDTORequest(Map<String, Object> user) {
		//this.id = user.get("")
		this.email = user.get("email").toString();
		this.fullname = user.get("fullname").toString();
		this.password = user.get("password").toString();
		this.phone = user.get("phone").toString();
		this.name = user.get("name").toString();
	}
}
