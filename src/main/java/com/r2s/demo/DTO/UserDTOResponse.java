package com.r2s.demo.DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.util.ObjectUtils;

import com.r2s.demo.model.Address;
//import com.r2s.demo.model.Cart;
import com.r2s.demo.model.User;

import lombok.Data;

@Data
public class UserDTOResponse {

	private Long id;
	private String fullName;
	private String phone;
	private String email;
	private Long cartId;
	private List<Map<String, Object>> address;
	// private Long cartId;

	public UserDTOResponse(User user) {
		this.id = user.getId();
		this.fullName = user.getName();
		this.phone = user.getPhone();
		this.email = user.getEmail();
		if (!ObjectUtils.isEmpty(user.getCart())) {
			this.cartId = user.getCart().getId();
		}

		this.address = new ArrayList<>();
		for (Address foundaddress : user.getAddresses()) {
			this.address.add(Map.of("city",foundaddress.getCity()));
		}
	}

}
