package com.ofss.main.service;

import com.ofss.main.domain.CustomerDetail;

import java.util.List;

public interface CustomerDetailService {
    boolean registerCustomer(CustomerDetail customerDetail);
    CustomerDetail getCustomerbyId(int customerId);
    CustomerDetail getCustomerByEmail(String email);
    List<CustomerDetail> getAllCustomers();
    void updateCustomer(CustomerDetail customerDetail);
    void deleteCustomer(int customerId);
    void updateLockedStatus(int customerId, boolean lockedStatus);
    void updateApprovalStatus(int customerDetail, boolean approvalStatus);
}