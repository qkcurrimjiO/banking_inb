package com.ofss.main.service;

import com.ofss.main.domain.AdminDetail;
import com.ofss.main.repository.AdminDetailRepository;
import com.ofss.main.repository.AdminDetailRepositoryImpl;

public class AdminDetailServiceImpl implements AdminDetailService {

    private AdminDetailRepository adminRepository = new AdminDetailRepositoryImpl();

    @Override
    public boolean authenticate(String username, String password) {
        AdminDetail adminDetail = adminRepository.findByUsername(username);
        return adminDetail != null && adminDetail.getPassword().equals(password);
    }
}