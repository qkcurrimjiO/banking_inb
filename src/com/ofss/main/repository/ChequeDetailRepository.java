package com.ofss.main.repository;

import com.ofss.main.domain.ChequeDetail;

import java.util.List;

public interface ChequeDetailRepository {
    void save(ChequeDetail chequeDetail);
    ChequeDetail findById(int chequeId);
    List<ChequeDetail> findAll();
    void updateStatus(int chequeId, String status);
}