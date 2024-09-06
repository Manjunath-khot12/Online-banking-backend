package com.excelR.banking.controller;

import java.util.HashMap;
import java.util.Map;
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
import com.excelR.banking.serviceImpl.EmailService;
import com.excelR.banking.serviceImpl.UserServiceImpl;

@RestController
@RequestMapping("/banking")
public class AuthController {
    
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    UserServiceImpl userService;
    
    @Autowired
    private EmailService emailService;
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            logger.warn("Login attempt with empty or null email");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Email is required"));
        }
        
        logger.info("Login Attempt with username: " + user.getEmail());
        Optional<User> optionalUser = userService.authenticateUser(user.getEmail());

        if (optionalUser.isPresent() && optionalUser.get().getPassword().equals(user.getPassword())) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("firstName", optionalUser.get().getFirstName());
            response.put("customerID",String.valueOf(optionalUser.get().getId()));
            return ResponseEntity.ok(response);
        }
        
        logger.warn("Invalid Login Attempt with username: " + user.getEmail());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
    }
    
    @PostMapping("/forgotpassword")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> requestData) {
        String email = requestData.get("email");

        if (email == null || email.isEmpty()) {
            logger.warn("Forgot password attempt with empty or null email");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Email is required"));
        }
        
        Optional<User> optionalUser = userService.authenticateUser(email);

        if (optionalUser.isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("firstName", optionalUser.get().getFirstName());
            response.put("password", optionalUser.get().getPassword());
            response.put("email", optionalUser.get().getEmail());
            String subject = "Forgot Password";
            String body = "Dear " + optionalUser.get().getFirstName()+ ",\n\n" +
                          "Your registrated password is :\n\n" +
                          "Username: " + optionalUser.get().getId() + "\n" +
                          "Password: " +  optionalUser.get().getPassword()+ "\n\n" +
                          
                          "Thank you .";
            
            emailService.sendRegistrationEmail(optionalUser.get().getEmail(), subject, body);
            return ResponseEntity.ok(response);
        }
        
       

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
    }}
