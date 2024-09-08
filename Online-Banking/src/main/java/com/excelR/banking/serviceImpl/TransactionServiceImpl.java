package com.excelR.banking.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excelR.banking.model.TransactionHistory;
import com.excelR.banking.repository.AccountRepository;
import com.excelR.banking.repository.TransactionHistoryRepository;
import com.excelR.banking.service.TransactionService;

import jakarta.transaction.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;
    

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public TransactionHistory saveTransaction(TransactionHistory transactionHistory) {
        return transactionHistoryRepository.save(transactionHistory);
    }
    
    @Override
    @Transactional
    public void updateAccountBalances(TransactionHistory transactionHistory) {
        try {
            

            // If destination account exists, deposit into it
            if (transactionHistory.getSourceAccount() != null) {
                accountRepository.depositAmount(
                    transactionHistory.getSourceAccount().getAccountNumber(),
                    transactionHistory.getAmount()
                );
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating account balances: " + e.getMessage(), e);
        }
    }
}
