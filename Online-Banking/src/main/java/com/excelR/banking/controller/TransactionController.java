package com.excelR.banking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @PostMapping("/withdrawAmount")
    public ResponseEntity<String> withdrawAmount(@RequestBody TransactionHistory transactionHistory)
    {
    	try
    	{
    		transactionService.saveTransaction(transactionHistory);
    		transactionService.updateWithdrawAccountBalances(transactionHistory);
    		
    	}
    	catch (Exception e) {
			e.printStackTrace();
			 return ResponseEntity.status(500).body("insufficent Balance.");
		}
    	return ResponseEntity.ok("Withdraw successful");
    }
    
    
    
    @PostMapping("/fundTransfer")
    public ResponseEntity<String> fundTransfer(@RequestBody TransactionHistory transactionHistory)
    {
    	try
    	{
    		transactionService.saveTransaction(transactionHistory);
    		transactionService.transferAmount(transactionHistory);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return ResponseEntity.status(500).body("insufficent Balance.");
    	}
    	return ResponseEntity.ok("Fund Transfered ");
    }
    
    @GetMapping("/transactionHistory/{accountNumber}")
    public List<TransactionHistory> findBySourceOrDestinationAccount(@PathVariable long accountNumber)
    {
    	return transactionService.findBySourceOrDestinationAccount(accountNumber);
    }
}
