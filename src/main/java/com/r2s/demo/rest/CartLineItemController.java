package com.r2s.demo.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.demo.DTO.CartLineItemDTOResponse;
import com.r2s.demo.Repository.CartLineItemRepository;
import com.r2s.demo.Repository.CartRepository;
import com.r2s.demo.Repository.VariantProductRepository;
import com.r2s.demo.contains.ResponseCode;
import com.r2s.demo.model.Cart;
import com.r2s.demo.model.CartLineItem;
import com.r2s.demo.model.VariantProduct;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "/cartLineItem")
public class CartLineItemController extends BaseRestController {

	@Autowired
	private CartLineItemRepository cartLineItemRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private VariantProductRepository variantProductRepository;
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("")
	public ResponseEntity<?> addCartLineItem(@RequestBody(required = false) Map<String, Object> newCartLine){
		try {
			if(ObjectUtils.isEmpty(newCartLine)) {
				return error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
		
			if(ObjectUtils.isEmpty(newCartLine.get("cartId"))||
					ObjectUtils.isEmpty(newCartLine.get("variantId"))
					||ObjectUtils.isEmpty(newCartLine.get("quantity"))) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			Long cartId = Long.parseLong(newCartLine.get("cartId").toString());
			Cart foundCart = this.cartRepository.findById(cartId).orElse(null);
			if(ObjectUtils.isEmpty(foundCart)) {
				return super.error(ResponseCode.NOT_FOUND.getCode(), ResponseCode.NOT_FOUND.getMessage());
			}
			Long variantId = Long.parseLong(newCartLine.get("variantId").toString());
			VariantProduct foundVariantProduct = this.variantProductRepository.findById(variantId).orElse(null);
			if(ObjectUtils.isEmpty(foundVariantProduct)) {
				return super.error(ResponseCode.NOT_FOUND.getCode(), ResponseCode.NOT_FOUND.getMessage());
			}
			
			CartLineItem newCartLineItem = new CartLineItem();
			newCartLineItem.setCart(foundCart);
			newCartLineItem.setVariantProduct(foundVariantProduct);
			newCartLineItem.setQuantity(Integer.parseInt(newCartLine.get("quantity").toString()));
			this.cartLineItemRepository.save(newCartLineItem);
			return super.success(newCartLine);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("")
	public ResponseEntity<?> getAllCartLineIem(@RequestParam(defaultValue = "-1") Integer status){
		try {
			if (!Arrays.asList(-1, 0, 1).contains(status)) {
				return error(ResponseCode.INVALID_VALUE.getCode(), ResponseCode.INVALID_VALUE.getMessage());
			}
			List<CartLineItem> cartLineItems;
			if(status == -1) {
				cartLineItems = this.cartLineItemRepository.findAll();
			} else if(status == 0) {
				cartLineItems = this.cartLineItemRepository.findAllByIsDeleted(false);
			}else {
				cartLineItems = this.cartLineItemRepository.findAllByIsDeleted(true);
			}
			List<CartLineItemDTOResponse> response = cartLineItems.stream().map(CartLineItemDTOResponse::new).toList();
			return super.success(response);

		} catch (Exception e) {
			e.printStackTrace();
	}
		return error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
}
}
