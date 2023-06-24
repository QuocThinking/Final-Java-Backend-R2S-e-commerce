package com.r2s.demo.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.demo.DTO.AddressDTORequest;
import com.r2s.demo.DTO.AddressDTOResponse;
import com.r2s.demo.Repository.AddressRepository;
import com.r2s.demo.Repository.UserRepository;
import com.r2s.demo.contains.ResponseCode;
import com.r2s.demo.model.Address;
import com.r2s.demo.model.User;
import com.r2s.demo.service.AddressService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping(path = "/address")
public class AddressController extends BaseRestController {

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired AddressService addressService;
	
//	@GetMapping
//	public ResponseEntity<?> getAddress() {
//		try {
//			List<Address> addresses = this.addressRepository.findAll();
//			return super.success(addresses.stream().map(AddressDTOResponse::new).toList());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
//	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("")
	public ResponseEntity<?> addAddress(@RequestBody (required = false) Map<String, Object> newAddress){
		try {
			if(ObjectUtils.isEmpty(newAddress)) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			AddressDTORequest addressDTORequest = new AddressDTORequest(newAddress);
			if(ObjectUtils.isEmpty(addressDTORequest.getUserId())||ObjectUtils.isEmpty(addressDTORequest.getCity())|| ObjectUtils.isEmpty(addressDTORequest.getCountry())
					|| ObjectUtils.isEmpty(addressDTORequest.getStreet())) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			long userId = Long.parseLong(newAddress.get("userId").toString());
			User founduser = this.userRepository.findById(userId).orElse(null);
			if(ObjectUtils.isEmpty(founduser)) {
				return super.error(ResponseCode.NOT_FOUND.getCode(), ResponseCode.NOT_FOUND.getMessage());
			}
//			Address insertAddress = this.addressService.addNewAddress(newAddress);
//			if(!ObjectUtils.isEmpty(insertAddress)) {
//				return super.success(new AddressDTOResponse(insertAddress));
//			}
			Address newAddresss = new Address();
			newAddresss.setCity(addressDTORequest.getCity());
			newAddresss.setCountry(addressDTORequest.getCountry());
			newAddresss.setStreet(addressDTORequest.getStreet());
			newAddresss.setId(userId);
			newAddresss.setUser(founduser);

			//Address insertedAddress = addressRepository.save(newAddresss);
			this.addressRepository.save(newAddresss);
			return super.success(newAddresss);

			//return super.success(new AddressDTOResponse(insertedAddress));	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("")
	public ResponseEntity<?> getAllAdress(){
		try {
			List<Address> addresses = this.addressRepository.findAll();
			// chuyen doi tuong addres sang addres dto
			return super.success(addresses.stream().map(AddressDTOResponse::new).toList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateAddress(@PathVariable long id, @RequestBody(required = false)
			Map<String, Object> newaddress){
		try {
			if(ObjectUtils.isEmpty(newaddress)){
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			if(ObjectUtils.isEmpty(newaddress.get("city"))||ObjectUtils.isEmpty(newaddress.get("country"))
					|| ObjectUtils.isEmpty(newaddress.get("street"))) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			
			Address foundAddress = this.addressRepository.findById(id).orElse(null);
			if(ObjectUtils.isEmpty(foundAddress)) {
				return super.error(ResponseCode.NOT_FOUND.getCode(), ResponseCode.NOT_FOUND.getMessage());
			}
			
			foundAddress.setCity(newaddress.get("city").toString());
			foundAddress.setCountry(newaddress.get("country").toString());
			foundAddress.setStreet(newaddress.get("street").toString());
			this.addressRepository.save(foundAddress);
			return super.success(newaddress);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
		
	}
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@DeleteMapping("/deleteById/{id}")
	@Transactional
	public ResponseEntity<?> deleteAddress(@PathVariable long id){
		try {
			Address foundAddress = this.addressRepository.findById(id).orElse(null);
			if(ObjectUtils.isEmpty(foundAddress)) {
				return super.error(ResponseCode.NOT_FOUND.getCode(), ResponseCode.NOT_FOUND.getMessage());
			}
			this.addressRepository.deleteById(id);
			return super.success(new AddressDTOResponse(foundAddress));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
}
