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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<?> createAccount(@RequestParam long userId, @RequestBody Account account) {
        try {
            account.setCreatedDate(LocalDate.now());
            Account createdAccount = accountServiceImpl.createAccount(userId, account);
            
            // Sending email after creating account
            List<AccountUserDTO> accountUser = accountServiceImpl.findAccountDetailsByAccountNumber(createdAccount.getAccountNumber());
            if (!accountUser.isEmpty()) {
                AccountUserDTO userDTO = accountUser.get(0);
                String firstName = userDTO.getFirstName();
                String accountType = userDTO.getAccountType();
                long accountNumber = userDTO.getAccountNumber();
                long adharaNumber = userDTO.getAdharaNumber();
                long phoneNumber = userDTO.getPhoneNumber();
                long deposit = userDTO.getInitialDeposit();
                String email = userDTO.getEmail();
                
                String subject = "Account Successfully Created";
                String body = "Dear " + firstName + ",\n\n" +
                              "We are pleased to inform you that your " + accountType + " account has been successfully created. Below are the details of your new account:\n\n" +
                              "Account Number: " + accountNumber + "\n" +
                              "Account Type: " + accountType + "\n" +
                              "Aadhaar Number: " + adharaNumber + "\n" +
                              "Registered Phone Number: " + phoneNumber + "\n" +
                              "Initial Balance: " + deposit + "\n\n" +
                              "Thank you for choosing our bank. Should you have any questions or require assistance, please do not hesitate to contact us.\n\n" +
                              "Best Regards,\n" +
                              "Customer Service Team\n" +
                              "ExcelR Bank";

                emailService.sendRegistrationEmail(email, subject, body);
            } else {
                throw new RuntimeException("Account details not found for email sending.");
            }

            return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/details/{accountNumber}")
    public List<AccountUserDTO> getAccountDetailsByCustomerId(@PathVariable Long accountNumber) {
        return accountServiceImpl.findAccountDetailsByAccountNumber(accountNumber);
    }
}
