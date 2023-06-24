package com.r2s.demo.contains;

public enum ResponseCode {
	SUCCESS(200,"OK"),
	NOT_FOUND(404, "Not found"),
	NO_PARAM(601,"No param"),
	USER_NOT_FOUND(4005, "User not found"),
	NO_CONTENT(701,"No Content"),
	INTERNAL_SERVER(901,"No Reason"),
	BAD_REQUEST(809,"No Request"),
	DATA_ALREADY_EXISTS(2023, "Data already exists"),
	CART_LINE_ITEMS_NOT_FOUND(4007,"CART LINE ITEMS NOT FOUND"), 
	INVALID_DATE_FORMAT(4008,"INVALID_DATE_FORMAT"), 
	ADDRESS_NOT_FOUND(4009,"ADDRESS_NOT_FOUND"), 
	INVALID_VALUE(3000, "Invalid value"),
	CART_NOT_FOUND(4010,"CART_NOT_FOUND");

	private int code;
	private String message;
	
	public int getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
	
	private ResponseCode( int code,String message) {
		this.code = code;
		this.message = message;
	}
}
