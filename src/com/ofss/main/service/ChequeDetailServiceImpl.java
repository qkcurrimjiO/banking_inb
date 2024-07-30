package com.ofss.main.service;

import com.ofss.main.domain.ChequeDetail;
import com.ofss.main.repository.ChequeDetailRepository;
import com.ofss.main.repository.ChequeDetailRepositoryImpl;

import java.util.List;

import com.ofss.main.domain.AccountDetail;
import com.ofss.main.repository.AccountDetailRepository;
import com.ofss.main.repository.AccountDetailRepositoryImpl;

public class ChequeDetailServiceImpl implements ChequeDetailService {

    private ChequeDetailRepository chequeRepository = new ChequeDetailRepositoryImpl();
    private AccountDetailRepository accountRepository = new AccountDetailRepositoryImpl();

    @Override
    public void issueCheque(ChequeDetail chequeDetail) {
        AccountDetail payerAccount = accountRepository.findByAccountId(chequeDetail.getPayerId());
        AccountDetail payeeAccount = accountRepository.findByAccountId(chequeDetail.getPayeeId());

        if (payerAccount != null && payeeAccount != null) {
            chequeRepository.save(chequeDetail);
        } else {
            throw new IllegalArgumentException("Either payer or payee account does not exist.");
        }
    }

    @Override
    public ChequeDetail getChequeById(int chequeId) {
        return chequeRepository.findById(chequeId);
    }

    @Override
    public List<ChequeDetail> getAllCheques() {
        return chequeRepository.findAll();
    }

    @Override
    public void approveOrRejectCheque(int chequeId, boolean isApproved) {
        ChequeDetail cheque = chequeRepository.findById(chequeId);
        if (cheque != null) {
            AccountDetail payerAccount = accountRepository.findByAccountId(cheque.getPayerId());
            if (isApproved) {
                if (payerAccount.getCurrentBalance() >= cheque.getAmount()) {
                    accountRepository.updateBalance(cheque.getPayerId(), payerAccount.getCurrentBalance() - cheque.getAmount());
                    AccountDetail payeeAccount = accountRepository.findByAccountId(cheque.getPayeeId());
                    accountRepository.updateBalance(cheque.getPayeeId(), payeeAccount.getCurrentBalance() + cheque.getAmount());
                    chequeRepository.updateStatus(chequeId, "Cleared");
                } else {
                    chequeRepository.updateStatus(chequeId, "Bounced");
                }
            } else {
                chequeRepository.updateStatus(chequeId, "Not Cleared");
            }
        }
    }
}