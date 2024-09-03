package com.excelR.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountUserDTO {
    private long accountNumber;
    private String firstName;
    private String accountType;
    private long initialDeposit;

    public AccountUserDTO(long accountNumber, String firstName, String accountType,long initialDeposit) {
        this.accountNumber = accountNumber;
        this.firstName = firstName;
        this.accountType = accountType;
        this.initialDeposit=initialDeposit;
    }

	public long getInitialDeposit() {
		return initialDeposit;
	}

	public void setInitialDeposit(long initialDeposit) {
		this.initialDeposit = initialDeposit;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}


   
}
