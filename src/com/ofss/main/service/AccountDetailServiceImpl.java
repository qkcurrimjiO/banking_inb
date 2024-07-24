package com.ofss.main.service;

import com.ofss.main.domain.AccountDetail;
import com.ofss.main.repository.AccountDetailRepository;
import com.ofss.main.repository.AccountDetailRepositoryImpl;

import com.ofss.main.domain.TransactionDetail;

import java.sql.Timestamp;
import java.util.List;

public class AccountDetailServiceImpl implements AccountDetailService {

    private AccountDetailRepository accountRepository = new AccountDetailRepositoryImpl();
    private TransactionDetailService transactionService = new TransactionDetailServiceImpl();

    @Override
    public void createAccount(int customerId, String accountType) {
        double minBalance = 0;
        double currentBalance = 0;
        boolean overdraftAvail = false;

        if (accountType.equalsIgnoreCase("Current")) {
            minBalance = 0;
            overdraftAvail = true;
        } else if (accountType.equalsIgnoreCase("Savings")) {
            minBalance = 5000;
            overdraftAvail = false;
        }

        AccountDetail newAccount = new AccountDetail(0, customerId, new Timestamp(System.currentTimeMillis()), accountType, minBalance, currentBalance, overdraftAvail);
        accountRepository.save(newAccount);
    }

    @Override
    public List<AccountDetail> getAccountsByCustomerId(int customerId) {
        return accountRepository.findByCustomerId(customerId);
    }

    @Override
    public void transferMoney(int fromAccountId, int toAccountId, double amount) {
        AccountDetail fromAccount = accountRepository.findByAccountId(fromAccountId);
        AccountDetail toAccount = accountRepository.findByAccountId(toAccountId);

        if (fromAccount != null && toAccount != null) {
            double newFromBalance = fromAccount.getCurrentBalance() - amount;
            double newToBalance = toAccount.getCurrentBalance() + amount;

            accountRepository.updateBalance(fromAccountId, newFromBalance);
            accountRepository.updateBalance(toAccountId, newToBalance);

            // Log the transaction for the sender
            TransactionDetail fromTransaction = new TransactionDetail(0, new Timestamp(System.currentTimeMillis()), "Withdraw", amount, fromAccountId);
            transactionService.logTransaction(fromTransaction);

            // Log the transaction for the receiver
            TransactionDetail toTransaction = new TransactionDetail(0, new Timestamp(System.currentTimeMillis()), "Deposit", amount, toAccountId);
            transactionService.logTransaction(toTransaction);
        }
    }
}