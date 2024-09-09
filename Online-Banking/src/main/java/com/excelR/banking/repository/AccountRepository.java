package com.excelR.banking.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.excelR.banking.dto.AccountUserDTO;
import com.excelR.banking.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    @Query("SELECT new com.excelR.banking.dto.AccountUserDTO(a.accountNumber, u.firstName, a.accountType, a.initialDeposit, a.adharaNumber,u.email,u.phoneNumber,a.createdDate) " +
            "FROM Account a JOIN a.customerId u WHERE u.id = :customerId")
     List<AccountUserDTO> findAccountDetailsByCustomerId(@Param("customerId") Long customerId);
	
	boolean existsByAccountNumber(long accountNumber);
	
	 @Query("SELECT a.initialDeposit FROM Account a WHERE a.accountNumber = :accountNumber")
	    long getBalanceByAccountNumber(@Param("accountNumber") long accountNumber);
	
	 @Modifying
	    @Query("UPDATE Account a SET a.initialDeposit = a.initialDeposit + :amount WHERE a.accountNumber = :accountNumber")
	    void depositAmount(long accountNumber, long amount);
	
	 @Modifying
	    @Query("UPDATE Account a SET a.initialDeposit = a.initialDeposit - :amount WHERE a.accountNumber = :accountNumber")
	    void withdrawAmount(long accountNumber, long amount);
	

}
 