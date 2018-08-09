package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
	UserEntity findByEmployeeIdAndCompanyCodeAndPassword(Integer employeeId,String companyCode, String password);

}
