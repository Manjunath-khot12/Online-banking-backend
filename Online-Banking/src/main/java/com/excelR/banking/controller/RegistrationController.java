package com.excelR.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
public class RegistrationController {

    @Autowired
    private UserServiceImpl userServiceImpl;
    
    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                return ResponseEntity.badRequest().body("Email cannot be null or empty.");
            }
            userServiceImpl.registerUser(user);
            
            String subject = "Registration Successful";
            String body = "Dear " + user.getFirstName() + ",\n\n" +
                          "Your registration is successful. Here are your login details:\n\n" +
                          "Username: " + user.getEmail() + "\n" +
                          "Password: " + user.getPassword() + "\n\n" +
                          "Thank you for Registering with us.";
            
            emailService.sendRegistrationEmail(user.getEmail(), subject, body);
            
            return ResponseEntity.ok("Registration successful");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("You Already have Account.");
        }
    }

}
