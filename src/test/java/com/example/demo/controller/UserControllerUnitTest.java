package com.example.demo.controller;

import static org.junit.Assert.*;

import org.apache.catalina.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.PayRollApplication;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.UserEntity;
import com.example.demo.service.IUserService;
@RunWith(SpringRunner.class)
@SpringBootTest(classes=PayRollApplication.class)
public class UserControllerUnitTest {

	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mvc;
	
	@MockBean
	private IUserService userService;
	
	@Before
	public void setUp() {
		mvc=MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void testLogin() {
		UserEntity user =new UserEntity();
		user.setEmployeeId(1);
		user.setCompanyCode("infogain");
		user.setName("sahil");
		user.setPassword("123");
		user.setPhoneNumber(567);
		user.setRole("user");
		
		Mockito.when(userService.findByEmployeeIdAndCompanyCodeAndPassword(user.getEmployeeId(), user.getCompanyCode(), user.getPassword())).thenReturn(user);
		
		UserDTO userDto = new UserDTO();
		userDto.setCompanyCode(user.getCompanyCode());
		userDto.setEmployeeId(user.getEmployeeId());
		userDto.setName(user.getName());
		userDto.setPhoneNumber(user.getPhoneNumber());
		userDto.setRole(user.getRole());
		
		String userJson="{\n" + 
				"        \"employeeId\":106226,\n" + 
				"        \"password\":\"java\",\n" + 
				"        \"companyCode\":\"INFODELHI\"\n" + 
				"    }";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/login").content(userJson).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		try {
			MvcResult result = mvc.perform(requestBuilder).andReturn();
			assertEquals(200,result.getResponse().getStatus());
		}catch (Exception e) {
			
		}
		
	}

	@Test
	public void testLoginWhenCredentialsAreWrong() {
		UserEntity user =new UserEntity();
		user.setEmployeeId(1);
		user.setCompanyCode("infogain");
		user.setName("sahil");
		user.setPassword("123");
		user.setPhoneNumber(567);
		user.setRole("user");
		
		Mockito.when(userService.findByEmployeeIdAndCompanyCodeAndPassword(user.getEmployeeId(), user.getCompanyCode(), user.getPassword())).thenReturn(null);
		String userJson="{\n" + 
				"        \"employeeId\":10622,\n" + 
				"        \"password\":\"jav\",\n" + 
				"        \"companyCode\":\"INFODEL\"\n" + 
				"    }";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/login").content(userJson).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		try {
			MvcResult result = mvc.perform(requestBuilder).andReturn();
			assertEquals(200,result.getResponse().getStatus());
		}catch (Exception e) {
			
		}
		
	}
}
