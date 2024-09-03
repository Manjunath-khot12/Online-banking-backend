package com.excelR.banking.service;

import java.util.List;

import com.excelR.banking.dto.AccountUserDTO;
import com.excelR.banking.model.Account;

public interface AccountService {
	 public Account createAccount(long userId, Account account);
	 public List<AccountUserDTO> getAccountDetailsByCustomerId(Long customerId);
	 
}
