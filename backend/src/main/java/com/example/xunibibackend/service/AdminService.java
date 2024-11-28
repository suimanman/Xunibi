package com.example.xunibibackend.service;

import com.example.xunibibackend.entity.Admin;

import java.util.List;

public interface AdminService {
    Admin login(String username, String password);

    Admin createAdmin(String username, String password);

    Admin updateAdmin(int adminId, String username, String password);

    boolean deleteAdmin(int adminId);

    List<Admin> getAllAdmins();

    Admin getAdminByName(String username);
}
