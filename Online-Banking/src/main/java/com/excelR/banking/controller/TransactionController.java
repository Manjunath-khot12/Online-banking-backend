package com.excelR.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.excelR.banking.model.TransactionHistory;
import com.excelR.banking.serviceImpl.TransactionServiceImpl;

@RestController
@RequestMapping("/banking")
public class TransactionController {

    @Autowired
    private TransactionServiceImpl transactionService;
    

    @PostMapping("/saveDeposit")
    public ResponseEntity<String> saveTransaction(@RequestBody TransactionHistory transactionHistory) {
        try {
            @SuppressWarnings("unused")
			TransactionHistory savedTransaction = transactionService.saveTransaction(transactionHistory);
            transactionService.updateAccountBalances(transactionHistory);
            return ResponseEntity.ok("Transaction successful");
        } catch (Exception e) {
            e.printStackTrace(); // Log error for debugging
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }
}
