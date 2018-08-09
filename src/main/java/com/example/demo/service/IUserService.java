package com.example.demo.service;

import com.example.demo.model.UserEntity;

public interface IUserService {
	public UserEntity findByEmployeeIdAndCompanyCodeAndPassword(Integer employeeId,String companyCode, String password);

}
