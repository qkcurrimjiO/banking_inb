package com.ofss.main.service;

import com.ofss.main.domain.TransactionDetail;
import com.ofss.main.repository.TransactionDetailRepository;
import com.ofss.main.repository.TransactionDetailRepositoryImpl;

public class TransactionDetailServiceImpl implements TransactionDetailService {

    private TransactionDetailRepository transactionRepository = new TransactionDetailRepositoryImpl();

    @Override
    public void logTransaction(TransactionDetail transactionDetail) {
        transactionRepository.save(transactionDetail);
    }
}