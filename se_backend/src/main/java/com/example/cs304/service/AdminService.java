package com.example.cs304.service;

import com.example.cs304.entity.Admin;
import com.example.cs304.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin validateAdmin(String aid, String password) {
        Admin admin = adminRepository.findByAccount(aid);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        } else {
            return null;
        }
    }
}
