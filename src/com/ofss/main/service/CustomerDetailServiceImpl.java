package com.ofss.main.service;

import com.ofss.main.domain.CustomerDetail;
import com.ofss.main.repository.CustomerDetailRepository;
import com.ofss.main.repository.CustomerDetailRepositoryImpl;

import java.util.List;

public class CustomerDetailServiceImpl implements CustomerDetailService {

    private CustomerDetailRepository customerRepository = new CustomerDetailRepositoryImpl();

    @Override
    public boolean registerCustomer(CustomerDetail customerDetail) {
        try {
            customerRepository.save(customerDetail);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public CustomerDetail getCustomerbyId(int customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public CustomerDetail getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public List<CustomerDetail> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public void updateCustomer(CustomerDetail customerDetail) {
        customerRepository.update(customerDetail);
    }

    @Override
    public void deleteCustomer(int customerId) {
        customerRepository.delete(customerId);
    }

    @Override
    public void updateLockedStatus(int customerId, boolean lockedStatus) {
        customerRepository.updateLockedStatus(customerId, lockedStatus);
        }
    
        @Override
    public void updateApprovalStatus(int customerId, boolean approvalStatus) {
        customerRepository.updateApprovalStatus(customerId, approvalStatus);
        }


    
}