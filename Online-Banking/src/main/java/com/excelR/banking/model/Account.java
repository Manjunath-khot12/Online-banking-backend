package com.excelR.banking.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;

    @Column(name = "account_Number", nullable = false)
    private long accountNumber;

    @Column(name = "adhara_number", nullable = false)
    private long adharaNumber;

    @Column(name = "pan_number", nullable = false)
    private long panNumber;

    @Column(name = "account_Type")
    private String accountType;

    @ManyToOne(fetch = FetchType.LAZY,cascade= {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "Customer_id", nullable = false)
    private User user;


	public long getAccountId() {
		return accountId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public long getAdharaNumber() {
		return adharaNumber;
	}

	public void setAdharaNumber(long adharaNumber) {
		this.adharaNumber = adharaNumber;
	}

	public long getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(long panNumber) {
		this.panNumber = panNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

    

}
