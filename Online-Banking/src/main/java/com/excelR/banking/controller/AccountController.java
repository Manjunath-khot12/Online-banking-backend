package com.excelR.banking.controller;



import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excelR.banking.dto.AccountUserDTO;
import com.excelR.banking.model.Account;
import com.excelR.banking.serviceImpl.AccountServiceImpl;
import com.excelR.banking.serviceImpl.EmailService;

@RestController
@RequestMapping("/banking")
public class AccountController {
    
	@Autowired
    private AccountServiceImpl accountServiceImpl;
	
	 @Autowired
	 private EmailService emailService;

    @PostMapping("/createAccount")
    public ResponseEntity<Account> createAccount(@RequestParam long userId, @RequestBody Account account) {
        try {
        	account.setCreatedDate(LocalDate.now());
            Account createdAccount = accountServiceImpl.createAccount(userId, account);
            
            
            // sending email after creating account
            String firstName=null;
            String accountType=null;
            long accountNumber=0;
            long adharaNumber=0;
            long phoneNumber=0;
            long deposit=0;
            String email=null;
            
            List<AccountUserDTO> accountUser=accountServiceImpl.getAccountDetailsByCustomerId(userId);
            for(AccountUserDTO accountUsers:accountUser)
            {
            	 firstName=accountUsers.getFirstName();
            	 accountType=accountUsers.getAccountType();
            	 accountNumber=accountUsers.getAccountNumber();
            	 adharaNumber=accountUsers.getAdharaNumber();
            	 phoneNumber=accountUsers.getPhoneNumber();
            	 deposit=accountUsers.getInitialDeposit();
            	 email=accountUsers.getEmail();
            	 
            }
            
            String subject = "Account Creation Successfully";
            String body = "Dear " +firstName + ",\n\n" +
                          "You have  successful Created "+accountType+" Account. Here are your Account details:\n\n" +
                          "Account Number : " + accountNumber + "\n" +
                          "Account Type: " + accountType+ "\n" +
                          "Adhara Number : " + adharaNumber + "\n" +
                          "Phone Number : " + phoneNumber + "\n" +
                          "Balance : " + deposit + "\n\n" +
                          "Thank you for creating account with us.";
            emailService.sendRegistrationEmail(email, subject, body);
            
           
            return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/details/{customerId}")
    public List<AccountUserDTO> getAccountDetailsByCustomerId(@PathVariable Long customerId) {
        return accountServiceImpl.getAccountDetailsByCustomerId(customerId);
    }
}
