package com.r2s.demo.DTO;

import org.springframework.util.ObjectUtils;

import com.r2s.demo.model.Address;
import com.r2s.demo.model.User;

import lombok.Data;

@Data
public class AddressDTOResponse {
	private Long id;
	private String city;
	private String country;
	private String street;
	private Long userID;
	private String name;
	
	
	public AddressDTOResponse(Address address) {
		this.id = address.getId();
		this.city = address.getCity();
		this.country = address.getCountry();
		this.street = address.getStreet();
		if(!ObjectUtils.isEmpty(address.getUser())) {
			User user = address.getUser();
			this.userID = user.getId();
			this.name = user.getName();
		}
	}
}
