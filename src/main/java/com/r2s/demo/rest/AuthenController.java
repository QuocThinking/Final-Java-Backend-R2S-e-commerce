package com.r2s.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.demo.DTO.AuthenDTORequest;
import com.r2s.demo.DTO.AuthenDTOResponse;
import com.r2s.demo.contains.ResponseCode;
import com.r2s.demo.uitls.JwtUtils;

@RestController
@RequestMapping
public class AuthenController extends BaseRestController {

	@Autowired
	private AuthenticationManager authenticationManager;

	

	@PostMapping("/login")
	public ResponseEntity<?> Login(@RequestBody AuthenDTORequest authen) {
		try {
			//String endCodePassword = this.passwordEncoder.encode(authen.getPassword());

			this.authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(authen.getUserName(), authen.getPassword()));

			String token = JwtUtils.generateToken(authen.getUserName());
			AuthenDTOResponse response = new AuthenDTOResponse(token, "Dang nhap thanh cong!");
			return success(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}

}
