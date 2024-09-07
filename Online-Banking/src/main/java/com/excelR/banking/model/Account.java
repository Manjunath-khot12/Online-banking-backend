package com.excelR.banking.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @Column(name = "account_Number", nullable = false,unique=true)
    private long accountNumber;

    @Column(name = "adhara_number", nullable = false)
    private long adharaNumber;

    @Column(name = "pan_number", nullable = false)
    private String panNumber;
    
    @Column(name="created_date")
    private LocalDate createdDate;

	@Column(name = "account_Type")
    private String accountType;

    @Column(name="initila_deposit", nullable=false)
    private long initialDeposit;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "customer_id", nullable = false)
    private User customerId;

    @OneToMany(mappedBy = "sourceAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionHistory> sentTransactions;
    
    @OneToMany(mappedBy = "destinationAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionHistory> receivedTransactions;

    // Getters and Setters
    public LocalDate getCreatedDate() {
 		return createdDate;
 	}

 	public void setCreatedDate(LocalDate createdDate) {
 		this.createdDate = createdDate;
 	}
    public long getAccountId() {
        return accountId;
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

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public long getInitialDeposit() {
        return initialDeposit;
    }

    public void setInitialDeposit(long initialDeposit) {
        this.initialDeposit = initialDeposit;
    }

    public User getCustomerId() {
        return customerId;
    }

    public void setCustomerId(User customerId) {
        this.customerId = customerId;
    }

    public List<TransactionHistory> getSentTransactions() {
        return sentTransactions;
    }

    public void setSentTransactions(List<TransactionHistory> sentTransactions) {
        this.sentTransactions = sentTransactions;
    }

    public List<TransactionHistory> getReceivedTransactions() {
        return receivedTransactions;
    }

    public void setReceivedTransactions(List<TransactionHistory> receivedTransactions) {
        this.receivedTransactions = receivedTransactions;
    }
}
