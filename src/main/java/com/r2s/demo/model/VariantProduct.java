package com.r2s.demo.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "variant_product")
public class VariantProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="variant_id")
	private Long id;
	
	private String name;
	private int model_year;
	private double price;
	private String color;
	private String size;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	@JsonBackReference
	private Product product;
	
	@OneToMany(mappedBy = "variantProduct")
	@JsonManagedReference
	private List<CartLineItem> cartLineItems;


	
}
