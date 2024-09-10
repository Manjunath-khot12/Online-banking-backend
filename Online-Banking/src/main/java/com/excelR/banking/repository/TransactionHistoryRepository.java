package com.excelR.banking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.excelR.banking.model.TransactionHistory;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
	@Query("SELECT t FROM TransactionHistory t WHERE t.sourceAccount = :accountNumber OR t.destinationAccount = :accountNumber")
    List<TransactionHistory> findBySourceOrDestinationAccount( Long accountNumber);
}
