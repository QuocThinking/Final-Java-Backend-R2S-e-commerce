package com.r2s.demo.model;

import java.util.List;
import java.util.Set;

import org.hibernate.annotations.OptimisticLock;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
//import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 @Column(name ="role_name")
	 private String name;
	 
	 
	 
	 @EqualsAndHashCode.Exclude
		@ToString.Exclude
		@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
		@JsonBackReference
		private Set<User> users;

//	public String getRoleName() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
