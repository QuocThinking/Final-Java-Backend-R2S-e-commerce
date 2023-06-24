package com.r2s.demo.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.demo.Repository.CartRepository;
import com.r2s.demo.Repository.UserRepository;
import com.r2s.demo.contains.ResponseCode;
import com.r2s.demo.model.Cart;
import com.r2s.demo.model.User;

@RestController
@RequestMapping(path = "/cart")
public class CartController extends BaseRestController {
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	
	private UserRepository userRepository;
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("")
	public ResponseEntity<?> addCart(@RequestBody (required = false) Map<String, Object> newCart){
		try {
			if(ObjectUtils.isEmpty(newCart) || ObjectUtils.isEmpty(newCart.get("userId"))) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			Long userId = Long.parseLong(newCart.get("userId").toString());
			User newUser = this.userRepository.findById(userId).orElse(null);
			if(ObjectUtils.isEmpty(newUser)) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			Cart cart = new Cart();
			cart.setUser(newUser);
			this.cartRepository.save(cart);
			return super.success(newCart);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
}
