package com.ofss.main.repository;

import com.ofss.main.domain.CustomerDetail;

import java.util.List;

public interface CustomerDetailRepository {
    void save(CustomerDetail customerDetail);
    CustomerDetail findById(int customerId);
    CustomerDetail findByEmail(String email);
    List<CustomerDetail> findAll();
    void update(CustomerDetail customerDetail);
    void delete(int customerId);
    void updateLockedStatus(int customerId, boolean lockedStatus);
    void updateApprovalStatus(int customerId, boolean approvalStatus);
}
