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

            // Generate a unique account number (e.g., 10-digit number)
            long accountNumber = generateUniqueAccountNumber();
            account.setAccountNumber(accountNumber);

            try {
                return accountRepository.save(account);
            } catch (Exception e) {
                throw new RuntimeException("Error saving account: " + e.getMessage());
            }
        } else {
            throw new RuntimeException("User not found with id " + userId);
        }
    }

    // Method to generate a unique account number
    private long generateUniqueAccountNumber() {
        long accountNumber;
        boolean exists;

        // Ensure the generated account number is unique
        do {
            accountNumber = (long) (Math.random() * 9000000000L) + 1000000000L; // Generate a 10-digit number
            exists = accountRepository.existsByAccountNumber(accountNumber); // Check if it already exists
        } while (exists);

        return accountNumber;
    }

    public List<AccountUserDTO> findAccountDetailsByAccountNumber(Long accountNumber) {
        return accountRepository.findAccountDetailsByAccountNumber(accountNumber);
    }
}
