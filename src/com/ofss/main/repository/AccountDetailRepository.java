
package com.ofss.main.repository;

import java.util.List;

import com.ofss.main.domain.AccountDetail;

public interface AccountDetailRepository {
    void save(AccountDetail accountDetail);   
    List<AccountDetail> findByCustomerId(int customerId);
    AccountDetail findByAccountId(int accountId);
    void updateBalance(int accountId, double newBalance);
}