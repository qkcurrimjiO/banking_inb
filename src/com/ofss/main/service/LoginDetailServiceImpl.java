package com.ofss.main.service;

import com.ofss.main.domain.LoginDetail;
import com.ofss.main.repository.LoginDetailRepository;
import com.ofss.main.repository.LoginDetailRepositoryImpl;
import com.ofss.main.repository.CustomerDetailRepository;
import com.ofss.main.repository.CustomerDetailRepositoryImpl;


public class LoginDetailServiceImpl implements LoginDetailService {

    private CustomerDetailRepository customerRepository = new CustomerDetailRepositoryImpl();
    private LoginDetailRepository loginRepository = new LoginDetailRepositoryImpl();

    @Override
    public boolean authenticate(String username, String password) {
        LoginDetail loginDetail = loginRepository.findByUsername(username);
        if (loginDetail != null) {
            if (loginDetail.getAttempts() >= 3) {
                customerRepository.updateLockedStatus(loginDetail.getCustomerId(), true);
                System.out.println("Account locked due to too many failed attempts.");
                return false;
            }
            if (loginDetail.getPassword().equals(password)) {
                resetAttempts(username);
                return true;
            }
            incrementAttempts(username);
        }
        return false;
    }

    @Override
    public void incrementAttempts(String username) {
        LoginDetail loginDetail = loginRepository.findByUsername(username);
        if (loginDetail != null) {
            int attempts = loginDetail.getAttempts() + 1;
            loginRepository.updateAttempts(username, attempts);
            if (attempts >= 3) {
                customerRepository.updateLockedStatus(loginDetail.getCustomerId(), true);
                System.out.println("Account locked due to too many failed attempts.");
            }
        }
    }

    @Override
    public void resetAttempts(String username) {
        loginRepository.updateAttempts(username, 0);
    }

    @Override
    public void updateAttempts(String username, int attempts) {
        loginRepository.updateAttempts(username, attempts);
    }

    @Override
    public LoginDetail findByCustomerID(int customerId) {
        return loginRepository.findByCustomerID(customerId);
    }

    @Override
    public LoginDetail save(LoginDetail newLogin) {
        return loginRepository.save(newLogin);
    }

    @Override
    public LoginDetail findByUsername(String username) {
        return loginRepository.findByUsername(username);
    }
    
}