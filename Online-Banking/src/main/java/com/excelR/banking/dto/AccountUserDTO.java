package com.excelR.banking.dto;

import java.time.LocalDate;


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
    private long adharaNumber;
    private String email;
    private long phoneNumber;
    private LocalDate createdDate;

    public AccountUserDTO(long accountNumber, String firstName, String accountType,long initialDeposit,long adharaNumber,String email,long phoneNumber,LocalDate createdDate) {
        this.accountNumber = accountNumber;
        this.firstName = firstName;
        this.accountType = accountType;
        this.initialDeposit=initialDeposit;
        this.adharaNumber=adharaNumber;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.createdDate=createdDate;
    }

    
	public LocalDate getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}


	public long getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public long getAdharaNumber() {
		return adharaNumber;
	}

	public void setAdharaNumber(long adharaNumber) {
		this.adharaNumber = adharaNumber;
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
