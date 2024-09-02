package com.excelR.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excelR.banking.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
