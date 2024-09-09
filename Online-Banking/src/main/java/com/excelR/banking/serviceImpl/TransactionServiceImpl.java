package com.excelR.banking.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excelR.banking.model.Account;
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
				accountRepository.depositAmount(transactionHistory.getSourceAccount().getAccountNumber(),
						transactionHistory.getAmount());
			}
		} catch (Exception e) {
			throw new RuntimeException("Error updating account balances: " + e.getMessage(), e);

		}
	}

	@Override
	@Transactional
	public void updateWithdrawAccountBalances(TransactionHistory transactionHistory) {
		try {
			long balance = accountRepository
					.getBalanceByAccountNumber(transactionHistory.getSourceAccount().getAccountNumber());
			if (transactionHistory.getSourceAccount() != null && transactionHistory.getAmount() <= balance) {
				accountRepository.withdrawAmount(transactionHistory.getSourceAccount().getAccountNumber(),
						transactionHistory.getAmount());
			} else {
				throw new RuntimeException("Insufficient balance.");
			}
		} catch (Exception e) {
			throw new RuntimeException("Error withdrawing from account: " + e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	public void transferAmount(TransactionHistory transactionHistory) {

	    try {
	        Account sourceAccount = transactionHistory.getSourceAccount();
	        Account destinationAccount = transactionHistory.getDestinationAccount();
	   
	        if (sourceAccount == null) {
	            throw new IllegalArgumentException("Source account cannot be null.");
	        }

	        long sourceBalance = accountRepository.getBalanceByAccountNumber(sourceAccount.getAccountNumber());
	        
			// Ensure the source account has sufficient balance for the transfer
	        if (transactionHistory.getAmount() > sourceBalance) {
	            throw new RuntimeException("Insufficient balance in source account.");
	        }
	        
	        accountRepository.withdrawAmount(sourceAccount.getAccountNumber(), transactionHistory.getAmount());
	        
	        // Validate destination account 
	        if (destinationAccount != null) {
	            if (sourceAccount.getAccountNumber() == destinationAccount.getAccountNumber()) {
	                throw new IllegalArgumentException("Cannot transfer to the same account.");
	            }

	            // Perform the deposit into the destination account
	            accountRepository.depositAmount(destinationAccount.getAccountNumber(), transactionHistory.getAmount());
	        }

	    } catch (IllegalArgumentException e) {
	        throw new RuntimeException("Invalid transfer: " + e.getMessage(), e);
	    } catch (Exception e) {
	        throw new RuntimeException("Error updating account balances: " + e.getMessage(), e);
	    }
	}
}
