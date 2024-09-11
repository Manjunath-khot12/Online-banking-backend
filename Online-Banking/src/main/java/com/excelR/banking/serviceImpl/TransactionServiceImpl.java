package com.excelR.banking.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excelR.banking.dto.AccountUserDTO;
import com.excelR.banking.model.Account;
import com.excelR.banking.model.TransactionHistory;
import com.excelR.banking.repository.AccountRepository;
import com.excelR.banking.repository.TransactionHistoryRepository;
import com.excelR.banking.service.TransactionService;

import jakarta.transaction.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionHistoryRepository transactionHistoryRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	TransactionHistoryRepository transactionReposotory;

	@Override
	public TransactionHistory saveTransaction(TransactionHistory transactionHistory) {
		return transactionHistoryRepository.save(transactionHistory);
	}

	@Override
	@Transactional
	public void updateAccountBalances(TransactionHistory transactionHistory) {

		try {

			// If destination account exists, deposit into it
			if (transactionHistory.getSourceAccount() != null) {

				accountRepository.depositAmount(transactionHistory.getSourceAccount().getAccountNumber(),
						transactionHistory.getAmount());

				List<AccountUserDTO> details = accountRepository
						.findAccountDetailsByAccountNumber(transactionHistory.getSourceAccount().getAccountNumber());
				// sending email after creating account
				if (!details.isEmpty()) {
					AccountUserDTO userDTO = details.get(0);
					String firstName = userDTO.getFirstName();
					String accountType = userDTO.getAccountType();
					long accountNumber = userDTO.getAccountNumber();
					long phoneNumber = userDTO.getPhoneNumber();
					long deposit = userDTO.getInitialDeposit();
					String email = userDTO.getEmail();

					String subject = "Successful Deposit Notification";

					String body = "Dear " + firstName + ",\n\n"
							+ "We are pleased to inform you that your deposit has been successfully credited to your "
							+ accountType + " account. Below are the details of your transaction:\n\n"
							+ "Account Number: " + accountNumber + "\n" + "Registered Phone Number: " + phoneNumber
							+ "\n" + "Credited Amount: " + transactionHistory.getAmount() + "\n" + "Available Balance: "
							+ deposit + "\n\n"
							+ "Thank you for choosing to bank with us. If you have any questions or need further assistance, please feel free to contact our customer service team.\n\n"
							+ "Best Regards,\n" + "Customer Service Team\n" + "Excler Bank";

					emailService.sendRegistrationEmail(email, subject, body);

				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Error updating account balances: " + e.getMessage(), e);

		}
	}

	@Override
	@Transactional
	public void updateWithdrawAccountBalances(TransactionHistory transactionHistory) {
		try {
			// Ensure sourceAccount is not null
			if (transactionHistory.getSourceAccount() == null) {
				throw new RuntimeException("Source account is null.");
			}

			long accountNumber = transactionHistory.getSourceAccount().getAccountNumber();
			long withdrawalAmount = transactionHistory.getAmount();

			// Fetch current balance
			long balance = accountRepository.getBalanceByAccountNumber(accountNumber);

			if (withdrawalAmount <= 0) {
				throw new RuntimeException("Invalid withdrawal amount: " + withdrawalAmount);
			}

			if (withdrawalAmount > balance) {
				throw new RuntimeException("Insufficient balance. Current balance: " + balance);
			}

			// Perform withdrawal
			accountRepository.withdrawAmount(accountNumber, withdrawalAmount);

			// Fetch updated account details for email notification
			List<AccountUserDTO> details = accountRepository.findAccountDetailsByAccountNumber(accountNumber);
			if (!details.isEmpty()) {
				AccountUserDTO userDTO = details.get(0);
				String firstName = userDTO.getFirstName();
				String accountType = userDTO.getAccountType();
				long updatedBalance = accountRepository.getBalanceByAccountNumber(accountNumber); // Fetch updated
																									// balance
				long phoneNumber = userDTO.getPhoneNumber();
				String email = userDTO.getEmail();

				// Prepare and send email
				String subject = "Successful Withdrawal Notification";
				String body = "Dear " + firstName + ",\n\n" + "We would like to inform you that a withdrawal of "
						+ withdrawalAmount + " has been successfully processed from your " + accountType
						+ " account. Below are the details of your transaction:\n\n" + "Account Number: "
						+ accountNumber + "\n" + "Registered Phone Number: " + phoneNumber + "\n" + "Withdrawn Amount: "
						+ withdrawalAmount + "\n" + "Available Balance: " + updatedBalance + "\n\n"
						+ "Thank you for choosing to bank with us. If you have any questions or need further assistance, please feel free to contact our customer service team.\n\n"
						+ "Best Regards,\n" + "Customer Service Team\n" + "ExcelR Bank";

				emailService.sendRegistrationEmail(email, subject, body);
			} else {
				throw new RuntimeException("Account details not found for account number: " + accountNumber);
			}
		} catch (Exception e) {
			// Log the exception for debugging purposes
			System.err.println("Exception occurred: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Error processing withdrawal: " + e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	public void transferAmount(TransactionHistory transactionHistory) {
		Account sourceAccount = transactionHistory.getSourceAccount();
		Account destinationAccount = transactionHistory.getDestinationAccount();
		long transferAmount = transactionHistory.getAmount();

		try {
			if (sourceAccount == null) {
				throw new IllegalArgumentException("Source account cannot be null.");
			}

			if (destinationAccount == null) {
				throw new IllegalArgumentException("Destination account cannot be null.");
			}

			if (sourceAccount.getAccountNumber() == destinationAccount.getAccountNumber()) {
				throw new IllegalArgumentException("Cannot transfer to the same account.");
			}

			long sourceBalance = accountRepository.getBalanceByAccountNumber(sourceAccount.getAccountNumber());

			if (transferAmount <= 0) {
				throw new IllegalArgumentException("Transfer amount must be greater than zero.");
			}

			if (transferAmount > sourceBalance) {
				throw new RuntimeException("Insufficient balance in source account.");
			}

			// Perform the withdrawal from the source account
			accountRepository.withdrawAmount(sourceAccount.getAccountNumber(), transferAmount);

			// Perform the deposit into the destination account
			accountRepository.depositAmount(destinationAccount.getAccountNumber(), transferAmount);

			// Fetch details for email notifications
			List<AccountUserDTO> sourceDetails = accountRepository
					.findAccountDetailsByAccountNumber(sourceAccount.getAccountNumber());
			List<AccountUserDTO> destinationDetails = accountRepository
					.findAccountDetailsByAccountNumber(destinationAccount.getAccountNumber());

			if (!sourceDetails.isEmpty()) {
				AccountUserDTO sourceUserDTO = sourceDetails.get(0);
				String sourceEmail = sourceUserDTO.getEmail();
				String sourceFirstName = sourceUserDTO.getFirstName();
				String sourceAccountType = sourceUserDTO.getAccountType();
				long sourceAccountNumber = sourceUserDTO.getAccountNumber();
				long sourcePhoneNumber = sourceUserDTO.getPhoneNumber();
				long sourceInitialDeposit = sourceUserDTO.getInitialDeposit();

				String sourceSubject = "Successful Transfer Notification";
				String sourceBody = "Dear " + sourceFirstName + ",\n\n"
						+ "We would like to inform you that an amount of " + transferAmount
						+ " has been successfully transferred from your " + sourceAccountType
						+ " account. Below are the details of the transaction:\n\n" + "Account Number: "
						+ sourceAccountNumber + "\n" + "Registered Phone Number: " + sourcePhoneNumber + "\n"
						+ "Transferred Amount: " + transferAmount + "\n" + "Available Balance: "
						+ (sourceInitialDeposit) + "\n\n"
						+ "Thank you for banking with us. If you have any questions or need further assistance, please contact our customer service team.\n\n"
						+ "Best Regards,\n" + "Customer Service Team\n" + "Excler Bank";

				emailService.sendRegistrationEmail(sourceEmail, sourceSubject, sourceBody);
			}

			if (!destinationDetails.isEmpty()) {
				AccountUserDTO destinationUserDTO = destinationDetails.get(0);
				String destinationEmail = destinationUserDTO.getEmail();
				String destinationFirstName = destinationUserDTO.getFirstName();
				String destinationAccountType = destinationUserDTO.getAccountType();
				long destinationAccountNumber = destinationUserDTO.getAccountNumber();
				long destinationPhoneNumber = destinationUserDTO.getPhoneNumber();
				long destinationInitialDeposit = destinationUserDTO.getInitialDeposit();

				String destinationSubject = "Successful Transfer Notification";
				String destinationBody = "Dear " + destinationFirstName + ",\n\n"
						+ "We would like to inform you that an amount of " + transferAmount
						+ " has been successfully deposited into your " + destinationAccountType
						+ " account. Below are the details of the transaction:\n\n" + "Account Number: "
						+ destinationAccountNumber + "\n" + "Registered Phone Number: " + destinationPhoneNumber + "\n"
						+ "Deposited Amount: " + transferAmount + "\n" + "New Balance: " + (destinationInitialDeposit)
						+ "\n\n"
						+ "Thank you for banking with us. If you have any questions or need further assistance, please contact our customer service team.\n\n"
						+ "Best Regards,\n" + "Customer Service Team\n" + "Excler Bank";

				emailService.sendRegistrationEmail(destinationEmail, destinationSubject, destinationBody);
			}

		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Invalid transfer request: " + e.getMessage(), e);
		} catch (Exception e) {
			throw new RuntimeException("Error during transfer operation: " + e.getMessage(), e);
		}
	}

	@Override
	public List<Object[]> getTransactionHistoryByAccountNumber(Long accountNumber) {
		 return transactionHistoryRepository.findTransactionDetailsByAccountNumber(accountNumber);
	}

	


}
