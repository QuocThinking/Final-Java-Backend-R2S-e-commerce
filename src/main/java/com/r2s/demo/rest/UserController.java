package com.r2s.demo.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.demo.DTO.UserDTORequest;
import com.r2s.demo.DTO.UserDTOResponse;
import com.r2s.demo.Repository.RoleRepository;
import com.r2s.demo.Repository.UserRepository;
import com.r2s.demo.contains.ResponseCode;
import com.r2s.demo.model.Cart;
import com.r2s.demo.model.User;
import com.r2s.demo.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController extends BaseRestController {
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;

	public ResponseEntity<?> getUserByName(@RequestParam String name) {
		return new ResponseEntity<>(this.userService.findByName("%" + name + "%"), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("")
	public ResponseEntity<?> getAllUser() {
		try {
			List<User> users = this.userService.getAlluser();
			List<UserDTOResponse> responses = new ArrayList<>();
			for (User user : users) {
				responses.add(new UserDTOResponse(user));
			}
			return super.success(responses);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> removeUser(@PathVariable long id) {
		try {
			User foundUser = this.userService.findUserById(id);
			if (ObjectUtils.isEmpty(foundUser)) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			this.userService.removeUser(id);
			return super.success(foundUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}


	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("")
	public ResponseEntity<?> addUser(@RequestBody(required = false) Map<String, Object> newuser) {
		try {
			if (ObjectUtils.isEmpty(newuser)) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}

			UserDTORequest userDTORequest = new UserDTORequest(newuser);
			if (ObjectUtils.isEmpty(userDTORequest.getFullname()) || ObjectUtils.isEmpty(userDTORequest.getEmail())
					|| ObjectUtils.isEmpty(userDTORequest.getPhone())
					|| ObjectUtils.isEmpty(userDTORequest.getPassword())
					|| ObjectUtils.isEmpty(userDTORequest.getName())) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			User founduser = this.userRepository.findByName(newuser.get("name").toString()).orElse(null);
			if (!ObjectUtils.isEmpty(founduser)) {
				return super.error(ResponseCode.DATA_ALREADY_EXISTS.getCode(),
						ResponseCode.DATA_ALREADY_EXISTS.getMessage());
			}

			User insertuser = new User();
			insertuser.setName(newuser.get("name").toString());
			insertuser.setEmail(newuser.get("email").toString());
			insertuser.setPhone(newuser.get("phone").toString());
			insertuser.setFullname(newuser.get("fullname").toString());
			insertuser.setRoles(this.roleRepository.findByName("USER"));
			insertuser.setPassword(this.passwordEncoder.encode(newuser.get("password").toString()));
			Cart cart = new Cart();
			// gán doi uong user cho cart
			cart.setUser(insertuser);
			// gan cart cho user
			insertuser.setCart(cart);
			this.userRepository.save(insertuser);
			if (!ObjectUtils.isEmpty(insertuser)) {
				return super.success(new UserDTOResponse(insertuser));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PutMapping("/{id}")
	public ResponseEntity<?> UpdateUserByMap(@PathVariable long id,
			@RequestBody(required = false) Map<String, Object> newUser) {
		try {
			if(ObjectUtils.isEmpty(newUser)) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			UserDTORequest userDTORequest = new UserDTORequest(newUser);
			if (ObjectUtils.isEmpty(newUser.get("name")) || ObjectUtils.isEmpty(newUser.get("password"))
					|| ObjectUtils.isEmpty(newUser.get("phone"))
					|| ObjectUtils.isEmpty(newUser.get("fullname"))
					|| ObjectUtils.isEmpty(newUser.get("email"))) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			User founduser = this.userService.findUserById(id);
			if (ObjectUtils.isEmpty(founduser)) {
				return super.error(ResponseCode.NOT_FOUND.getCode(), ResponseCode.NOT_FOUND.getMessage());
			}
			founduser.setEmail(userDTORequest.getEmail());
			founduser.setFullname(userDTORequest.getFullname());
			founduser.setPassword(this.passwordEncoder.encode(userDTORequest.getPassword()));
			founduser.setPhone(userDTORequest.getPhone());
			founduser.setName(newUser.get("name").toString());
			this.userRepository.save(founduser);
			return super.success(founduser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
}
