package com.excelR.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excelR.banking.model.TransactionHistory;


public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

}
