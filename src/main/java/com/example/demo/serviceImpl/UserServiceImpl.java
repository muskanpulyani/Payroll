package com.example.demo.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.IUserService;
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository; 
	@Override
	public UserEntity findByEmployeeIdAndCompanyCodeAndPassword(Integer employeeId,String companyCode, String password) {
		
		return userRepository.findByEmployeeIdAndCompanyCodeAndPassword(employeeId,companyCode, password);
	}

}
