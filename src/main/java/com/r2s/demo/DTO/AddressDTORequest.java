package com.r2s.demo.DTO;

import java.util.Map;

import com.r2s.demo.model.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTORequest {

	private Long id;
	
	private String city;
	
	private String country;
	
	private String street;
	
	private Long userId;
	
	public AddressDTORequest(Map<String , Object> address) {
		this.userId = Long.parseLong(address.get("userId").toString());
		this.city = address.get("city").toString();
		this.country = address.get("country").toString();
		this.street = address.get("street").toString();
	}
}
