package com.excelR.banking.service;

import java.util.Optional;

import com.excelR.banking.model.User;

public interface UserService {
  
	 public User registerUser(User user);
	 public Optional<User> authenticateUser(String  email);
	 public Optional<User> findById(long customerId);
	
}
