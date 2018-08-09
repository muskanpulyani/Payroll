package com.example.demo.dto;

public class ResponseDTO {
	private String message;
	private String token;
	private Object response;
	private Integer code;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Object getResponse() {
		return response;
	}
	public void setResponse(Object response) {
		this.response = response;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	

}
