package com.r2s.demo.DTO;

import java.util.Date;

import org.springframework.util.ObjectUtils;

import com.r2s.demo.model.Address;
import com.r2s.demo.model.Order;
import com.r2s.demo.model.User;

//import java.sql.Date;

import lombok.Data;

@Data
public class OrderDTOResponse {

	private Long id;
	private Date deliveryTime;
	private Date orderDate;
	private Double totalPrice;
	private Long addressId;
	private Long userId;
	private String name;
	private String city;
	private String street;
	public OrderDTOResponse(Order order) {
		this.id = order.getId();
		this.deliveryTime = order.getDeliveryTime();
		this.orderDate = order.getOrderDate();
		this.totalPrice = order.getPrice();
		if(!ObjectUtils.isEmpty(order.getAddress())) {
			Address address = order.getAddress();
			this.addressId = address.getId();
			this.city = address.getCity();
			this.street = address.getStreet();
		}
		if(!ObjectUtils.isEmpty(order.getUser())) {
			User user = order.getUser();
			this.userId = user.getId();
			this.name = user.getName();
		}
	}
}
