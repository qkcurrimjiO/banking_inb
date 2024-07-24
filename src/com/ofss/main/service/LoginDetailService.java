package com.ofss.main.service;

import com.ofss.main.domain.LoginDetail;

public interface LoginDetailService {
    boolean authenticate(String username, String password);
    void incrementAttempts(String username);
    void resetAttempts(String username);
    void updateAttempts(String username, int attempts);
    LoginDetail findByCustomerID(int customerId);
    LoginDetail save(LoginDetail newLogin);
    LoginDetail findByUsername(String username);
}