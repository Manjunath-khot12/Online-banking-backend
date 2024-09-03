package com.excelR.banking.controller;



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

@RestController
@RequestMapping("/banking")
public class AccountController {
    
	@Autowired
    private AccountServiceImpl accountServiceImpl;

    @PostMapping("/createAccount")
    public ResponseEntity<Account> createAccount(@RequestParam long userId, @RequestBody Account account) {
        try {
            Account createdAccount = accountServiceImpl.createAccount(userId, account);
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
