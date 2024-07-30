package com.ofss.main.service;
import com.ofss.main.domain.ChequeDetail;

import java.util.List;

public interface ChequeDetailService {
    void issueCheque(ChequeDetail chequeDetail);
    ChequeDetail getChequeById(int chequeId);
    List<ChequeDetail> getAllCheques();
    void approveOrRejectCheque(int chequeId, boolean isApproved);
}