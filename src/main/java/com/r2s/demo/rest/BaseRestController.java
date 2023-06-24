package com.r2s.demo.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseRestController {
	private int code;
	private String message;
	private Object data;
	
	public ResponseEntity<?>success(Object data){
		Map<String, Object> response = new HashMap<>();
		response.put("code", 200);
		response.put("message", "OK");
		response.put("data", data);
		return new ResponseEntity<Map>(response,HttpStatus.OK);
	}
	
	public ResponseEntity<?>error(int code, String message){
		Map<String, Object> response = new HashMap<>();
		response.put("code", code);
		response.put("message", message);
		response.put("data", null);
		return new ResponseEntity<Map>(response,HttpStatus.OK);
	}
}
