package com.example.demo.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.UserEntity;
import com.example.demo.service.IRedis;
import com.example.demo.service.IUserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	private IUserService userService;
	@Autowired
	private IRedis redis;

	@PostMapping("/login")
	public ResponseDTO login(@RequestBody UserEntity user) {
		ResponseDTO response = new ResponseDTO();
		UserEntity user1 = userService.findByEmployeeIdAndCompanyCodeAndPassword(user.getEmployeeId(),
				user.getCompanyCode(), user.getPassword());
		if (user1 == null) {
			response.setCode(HttpStatus.UNAUTHORIZED.value());
			response.setMessage("wrong");

		} else {
			String jwt = null;
			try {
				jwt = Jwts.builder().setSubject(user1.getName()).claim("scope", user1.getRole())
						.signWith(SignatureAlgorithm.HS256, "secret".getBytes("UTF-8"))
						.setIssuedAt(new Date(System.currentTimeMillis()))
						.setExpiration(new Date(System.currentTimeMillis() + 10000)).compact();
				redis.setValue(jwt, user1.getEmployeeId());
			} catch (UnsupportedEncodingException e) {

			}
			UserDTO userDto = new UserDTO();
			userDto.setCompanyCode(user1.getCompanyCode());
			userDto.setEmployeeId(user1.getEmployeeId());
			userDto.setName(user1.getName());
			userDto.setPhoneNumber(user1.getPhoneNumber());
			userDto.setRole(user1.getRole());
			response.setResponse(userDto);
			response.setCode(HttpStatus.OK.value());
			response.setMessage("User successfully logged in");
			response.setToken(jwt);

		}
		return response;
	}

	@GetMapping("/logout")
	public ResponseDTO logout(HttpServletRequest request) {
		Object token = redis.getValue(request.getHeader("Authorization"));
		ResponseDTO response = new ResponseDTO();
		if(token == null) {
			response.setCode(HttpStatus.NOT_FOUND.value());
			response.setToken(null);
			response.setMessage("Token not found");
			response.setResponse(null);
			
		}
		else 
		{
			redis.deleteValue(request.getHeader("Authorization"));
			response.setCode(HttpStatus.OK.value());
			response.setToken(null);
			response.setMessage("Successfully logged out");
			response.setResponse(null);
		}
		
		return response;
	}

}
