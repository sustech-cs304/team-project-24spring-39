package com.example.cs304.repository;

import com.example.cs304.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByAccount(String aid);
}
