package com.ofss.main.service;

import java.util.List;

import com.ofss.main.domain.AccountDetail;

public interface AccountDetailService {
    void createAccount(int customerId, String accountType);
    List<AccountDetail> getAccountsByCustomerId(int customerId);
    void transferMoney(int fromAccountId, int toAccountId, double amount);
}