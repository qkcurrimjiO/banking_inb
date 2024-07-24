package com.ofss.main.repository;

import com.ofss.main.domain.LoginDetail;

public interface LoginDetailRepository {
    LoginDetail save(LoginDetail loginDetail);
    LoginDetail findByUsername(String username);
    void updateAttempts(String username, int attempts);
    LoginDetail findByCustomerID(int customer_id);
}