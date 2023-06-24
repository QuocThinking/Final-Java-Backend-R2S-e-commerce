package com.r2s.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	private String fullname;
	@Column(name ="username" ,nullable = false,unique = true )
	private String name;
	
	private String phone;
	
	private String email;
	private String password;
	
	@OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
	@JsonBackReference
	private Cart cart;
	
	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<Address> addresses = new ArrayList<>();
	
	@OneToMany(mappedBy = "user")
	@JsonManagedReference
	private List<Order> orders;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@JsonManagedReference
	private Set<Role> roles;
}
