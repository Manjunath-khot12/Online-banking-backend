package com.excelR.banking.model;

import java.time.LocalDate;


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
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="transaction_history")
public class TranscationHistory {
	
	@Id
	@Column(name="transaction_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long transactionId;
	
	@Column(name="transaction_type", nullable=false)
	private String transactionType;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="source_account", referencedColumnName = "account_Number", nullable = false)
	private Account sourceAccount;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="destination_account", referencedColumnName = "account_Number", nullable = true)
	private Account destinationAccount;
	
	@Column(name="transaction_info")
	private String transactionInfo;
	
	@Column(name="amount", nullable=false)
	private long amount;
	
	@Column(name="transaction_date", nullable=false)
	private LocalDate transactionDate;

	// Getters and Setters
	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Account getSourceAccount() {
		return sourceAccount;
	}

	public void setSourceAccount(Account sourceAccount) {
		this.sourceAccount = sourceAccount;
	}

	public Account getDestinationAccount() {
		return destinationAccount;
	}

	public void setDestinationAccount(Account destinationAccount) {
		this.destinationAccount = destinationAccount;
	}

	public String getTransactionInfo() {
		return transactionInfo;
	}

	public void setTransactionInfo(String transactionInfo) {
		this.transactionInfo = transactionInfo;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}
}



//SELECT 
//th.transaction_date, 
//th.amount, 
//th.transaction_info, 
//th.transaction_type,
//sa.account_number AS source_account_number,
//da.account_number AS destination_account_number
//FROM 
//transaction_history th
//LEFT JOIN account sa ON th.source_account = sa.account_id
//LEFT JOIN account da ON th.destination_account = da.account_id;



// particular person account number
//mysql> SELECT
//->     th.transaction_date,
//->     th.amount,
//->     th.transaction_info,
//->     th.transaction_type,
//->     sa.account_number AS source_account_number,
//->
//->     da.account_number AS destination_account_number
//-> FROM
//->     transaction_history th
//-> LEFT JOIN account sa ON th.source_account = sa.account_id
//-> LEFT JOIN account da ON th.destination_account = da.account_id
//-> WHERE
//->     sa.account_number = 9674845960
//->     OR da.account_number = 9674845960;
