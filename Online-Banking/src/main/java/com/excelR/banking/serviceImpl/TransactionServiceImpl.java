package com.excelR.banking.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excelR.banking.model.TransactionHistory;

import com.excelR.banking.repository.TransactionHistoryRepository;
import com.excelR.banking.service.TransactionService;


@Service
public class TransactionServiceImpl implements TransactionService {
    
	@Autowired
	TransactionHistoryRepository transactionHistoryRepository;
	
	@Override
	public TransactionHistory saveTranscation(TransactionHistory transcationHistory) {
		return transactionHistoryRepository.save(transcationHistory);
	}

}
