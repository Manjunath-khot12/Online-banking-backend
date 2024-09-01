package com.excelR.banking.service;

import com.excelR.banking.model.User;
import com.excelR.banking.repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        return userRepository.save(user);
    }
    
    public Optional<User> authenticateUser(String  email)
    {
    	return userRepository.getByEmail(email);
    }
}
