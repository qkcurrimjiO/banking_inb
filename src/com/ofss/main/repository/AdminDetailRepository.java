package com.ofss.main.repository;

import com.ofss.main.domain.AdminDetail;

public interface AdminDetailRepository {
    AdminDetail findByUsername(String username);
}