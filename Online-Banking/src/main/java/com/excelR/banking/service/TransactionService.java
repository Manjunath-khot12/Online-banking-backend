package com.excelR.banking.service;

import com.excelR.banking.model.TransactionHistory;

public interface TransactionService {
    TransactionHistory saveTransaction(TransactionHistory transactionHistory);
    void updateAccountBalances(TransactionHistory transactionHistory);
    void updateWithdrawAccountBalances(TransactionHistory transactionHistory);
    void transferAmount(TransactionHistory transactionHistory);
}
