package com.excelR.banking.controller;

import java.time.LocalDate;

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
	TransactionServiceImpl transactionServiceImp;
	
	@PostMapping("/deposit")
	public ResponseEntity<String> saveDespoitTransaction(@RequestBody TransactionHistory transactionHistory)
	{
		transactionHistory.setTransactionDate(LocalDate.now());
		transactionServiceImp.saveTranscation(transactionHistory);
		return ResponseEntity.ok("Despoit successful");
	}

}
