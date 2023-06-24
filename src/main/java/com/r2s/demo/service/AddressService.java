package com.r2s.demo.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.r2s.demo.Repository.AddressRepository;
import com.r2s.demo.model.Address;
import com.r2s.demo.model.User;

@Service
public class AddressService {
	
	@Autowired
	private AddressRepository addressRepository;

	private Address GenerateAddress(Map<String, Object> newaddress) {
		Address address = new Address();
		address.setCity(newaddress.get("city").toString());
		address.setCountry(newaddress.get("country").toString());
		address.setStreet(newaddress.get("street").toString());
		return address;
	}
	
	public Address addNewAddress(Map<String, Object> newAddress) {
		Address address = GenerateAddress(newAddress);
		User user = new User();
		user.setId(user.getId());
		address.setUser(user);
		address = this.addressRepository.save(address);
		return address;
	}
}
