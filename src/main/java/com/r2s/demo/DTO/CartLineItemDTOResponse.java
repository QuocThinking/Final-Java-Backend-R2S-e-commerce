package com.r2s.demo.DTO;

import java.util.List;

import org.springframework.util.ObjectUtils;

import com.r2s.demo.model.Cart;
import com.r2s.demo.model.CartLineItem;
import com.r2s.demo.model.VariantProduct;

import lombok.Data;

@Data
public class CartLineItemDTOResponse {

	private Long id;
	private int quantiy;
	private Long cartId;
	private Long variantId;
	private Long userId;
	private String name;
	private Double price;
	private Double totalPrice;
	
	public CartLineItemDTOResponse (CartLineItem cartLineItem) {
		this.id = cartLineItem.getId();
		this.quantiy = cartLineItem.getQuantity();
		
		if(!ObjectUtils.isEmpty(cartLineItem.getCart())) {
			Cart cart = cartLineItem.getCart();
			this.cartId = cart.getId();
			this.userId = cart.getUser().getId();
		}
		
		double totalPrices = 0.0;
		if(!ObjectUtils.isEmpty(cartLineItem.getVariantProduct())) {
			VariantProduct variantProduct = cartLineItem.getVariantProduct();
			this.variantId = variantProduct.getId();
			this.name = variantProduct.getName();
			this.price = variantProduct.getPrice();
			totalPrices = cartLineItem.getQuantity() * variantProduct.getPrice();
			this.totalPrice = totalPrices;
		}
	}
}
