package com.excelR.banking.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excelR.banking.dto.AccountUserDTO;
import com.excelR.banking.model.Account;
import com.excelR.banking.model.User;
import com.excelR.banking.repository.AccountRepository;
import com.excelR.banking.repository.UserRepository;
import com.excelR.banking.service.AccountService;

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
   
	public List<AccountUserDTO> getAccountDetailsByCustomerId(Long customerId) {
        return accountRepository.findAccountDetailsByCustomerId(customerId);
    }
}
