package com.excelR.banking.controller;

import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excelR.banking.model.User;
import com.excelR.banking.service.UserServiceImpl;

@RestController
@RequestMapping("/banking")
public class AuthController {
	
	private static final org.slf4j.Logger logger=LoggerFactory.getLogger(AuthController.class);

	@Autowired
	UserServiceImpl userService;
	
	@PostMapping("/login")
	public ResponseEntity<String> authenticateUser(@RequestBody User user) {
	    if (user.getEmail() == null || user.getEmail().isEmpty()) {
	        logger.warn("Login attempt with empty or null email");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is required");
	    }
	    
	    logger.info("Login Attempt with username: " + user.getEmail());
	    Optional<User> optionalUser = userService.authenticateUser(user.getEmail());

	    if (optionalUser.isPresent() && optionalUser.get().getPassword().equals(user.getPassword())) {
	        return ResponseEntity.ok().build();
	    }
	    logger.warn("Invalid Login Attempt with username: " + user.getEmail());
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
		
		
	
	}

}
