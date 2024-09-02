package com.excelR.banking.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excelR.banking.model.Account;
import com.excelR.banking.model.User;
import com.excelR.banking.repository.AccountRepository;
import com.excelR.banking.repository.UserRepository;

@Service
public class AccountServiceImpl implements AccountService {
    
	@Autowired
	AccountRepository accountRepository;
	
    @Autowired
    UserRepository userRepository;
    
	@Override
	public Account createAccount(long userId, Account account) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            account.setCustomerId(user.get()); // Set the User as the customer
            return accountRepository.save(account);
        } else {
            throw new RuntimeException("User not found with id " + userId);
        }
    }

}
