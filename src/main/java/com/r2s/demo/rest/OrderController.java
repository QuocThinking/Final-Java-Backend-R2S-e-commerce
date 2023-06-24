package com.r2s.demo.rest;

//import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
//import java.util.Optional;

//import javax.swing.text.DateFormatter;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.demo.DTO.OrderDTOResponse;
import com.r2s.demo.Repository.AddressRepository;
import com.r2s.demo.Repository.CartLineItemRepository;
import com.r2s.demo.Repository.CartRepository;
import com.r2s.demo.Repository.OrderRepository;
import com.r2s.demo.Repository.UserRepository;
import com.r2s.demo.contains.ResponseCode;
import com.r2s.demo.model.Address;
import com.r2s.demo.model.Cart;
import com.r2s.demo.model.CartLineItem;
import com.r2s.demo.model.Order;
import com.r2s.demo.model.User;

//import jakarta.transaction.Transactional;

@RestController
@RequestMapping(path = "/order")
public class OrderController extends BaseRestController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CartLineItemRepository cartLineItemRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private AddressRepository addressRepository;

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("")
	@Transactional(noRollbackFor = { Exception.class })
	public ResponseEntity<?> addOrder(@RequestBody(required = false) Map<String, Object> newOrder) {
		try {
			if (ObjectUtils.isEmpty(newOrder)) {
				return error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			if (ObjectUtils.isEmpty(newOrder.get("userId")) || ObjectUtils.isEmpty(newOrder.get("addressId"))
					|| ObjectUtils.isEmpty(newOrder.get("deliveryTime"))
					) {
				return error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			Long userId = Long.parseLong(newOrder.get("userId").toString());
			User newUser = this.userRepository.findById(userId).orElse(null);
			if (ObjectUtils.isEmpty(newUser)) {
				return error(ResponseCode.USER_NOT_FOUND.getCode(), ResponseCode.USER_NOT_FOUND.getMessage());
			}
			Long addressId = Long.parseLong(newOrder.get("addressId").toString());
			Address newadAddress = this.addressRepository.findById(addressId).orElse(null);
			if (ObjectUtils.isEmpty(newadAddress)) {
				return error(ResponseCode.ADDRESS_NOT_FOUND.getCode(), ResponseCode.ADDRESS_NOT_FOUND.getMessage());
			}

//			Cart cart = cartRepository.findUserById(userId);
//			if (ObjectUtils.isEmpty(cart)) {
//				return error(ResponseCode.CART_NOT_FOUND.getCode(), ResponseCode.CART_NOT_FOUND.getMessage());
//			}
			//Cart cart = new Cart();
			Long cartId = Long.parseLong(newOrder.get("cartId").toString());
			Cart newCart = this.cartRepository.findById(cartId).orElse(null);
			List<CartLineItem> cartLineItems = this.orderRepository.findAllCartLineItemsByCartId(cartId);
			if(ObjectUtils.isEmpty(newOrder.get("cartId"))||ObjectUtils.isEmpty(cartLineItems)||ObjectUtils.isEmpty(newCart)) {
				 return super.error(ResponseCode.CART_LINE_ITEMS_NOT_FOUND.getCode(),
				    		ResponseCode.CART_LINE_ITEMS_NOT_FOUND.getMessage());
			}
			for (CartLineItem item : cartLineItems) {
			    item.setDeleted(true);
			}
			
//			Optional<Cart> newCartt = this.cartRepository.findById(cartId);
//			if(ObjectUtils.isEmpty(newCartt)) {
//				 return super.error(ResponseCode.CART_NOT_FOUND.getCode(), ResponseCode.CART_NOT_FOUND.getMessage());
//			}

//			for (CartLineItem cartLineItem : cartLineItems) {
//				cartLineItem.setDeleted(true);
//				this.cartLineItemRepository.save(cartLineItem);
//			}
			//List<CartLineItem> cartLineItems = cartLineItemRepository.findAllByCartId(cart.getId());
//			if (ObjectUtils.isEmpty(cartLineItems)) {
//				return error(ResponseCode.CART_LINE_ITEMS_NOT_FOUND.getCode(),
//						ResponseCode.CART_LINE_ITEMS_NOT_FOUND.getMessage());
//			}
			  double totalPrice = 0;
			for (CartLineItem cartLineItem : cartLineItems) {
				
				totalPrice = cartLineItem.getVariantProduct().getPrice() * cartLineItem.getQuantity();
				//cartLineItem.setDeleted(true);
				//cartLineItemRepository.save(cartLineItem);
			}
			
			Order newoOrder = new Order();
			String deliveryTimestr = newOrder.get("deliveryTime").toString();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss");
			Date deliveryTime = null;
			try {
				deliveryTime = dateFormat.parse(deliveryTimestr);
			} catch (Exception e) {
				return super.error(ResponseCode.INVALID_DATE_FORMAT.getCode(),
						ResponseCode.INVALID_DATE_FORMAT.getMessage());
			}
			newoOrder.setDeliveryTime(deliveryTime);
			newoOrder.setAddress(newadAddress);
			newoOrder.setUser(newUser);
			//newoOrder.setPrice(Double.parseDouble(newOrder.get("price").toString()));
			newoOrder.setPrice(totalPrice);
			newoOrder.setOrderDate(new Date());
			this.orderRepository.save(newoOrder);

			return super.success(newOrder);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("")
	public ResponseEntity<?> getAllOrder(){
		try {
			List<Order> orders = this.orderRepository.findAll();
			List<OrderDTOResponse> responses = orders.stream().map(OrderDTOResponse::new).toList();
			return super.success(responses);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
}
