package com.excelR.banking.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.excelR.banking.model.TransactionHistory;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
	
	/**
     * Fetches all transaction histories where the source or destination account matches the given account number.
     *
     * @param accountNumber the account number to filter transactions
     * @return a list of matching TransactionHistory records
     */
	@Query("SELECT th.transactionId AS transactionId, th.amount AS amount, th.transactionDate AS transactionDate, " +
	           "th.transactionInfo AS transactionInfo, th.transactionType AS transactionType, " +
	           "th.destinationAccount.accountNumber AS destinationAccount, th.sourceAccount.accountNumber AS sourceAccount " +
	           "FROM TransactionHistory th " +
	           "WHERE th.sourceAccount.accountNumber = :accountNumber " +
	           "OR th.destinationAccount.accountNumber = :accountNumber")
	    List<Object[]> findTransactionDetailsByAccountNumber(@Param("accountNumber") Long accountNumber);
}
