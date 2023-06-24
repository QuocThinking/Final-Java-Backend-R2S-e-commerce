package com.r2s.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_line_item")
public class CartLineItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cartline_ID")
	private Long id;
	
	@Column(name = "is_deleted",columnDefinition = "boolean default false")
	private boolean isDeleted;
	
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "cart_id")
	@JsonBackReference
	private Cart cart;
	
	@ManyToOne
	@JoinColumn(name = "variant_id")
	@JsonBackReference
	private VariantProduct variantProduct;
	
	
	
}
