package com.example.xunibibackend.service.impl;

import com.example.xunibibackend.entity.Admin;
import com.example.xunibibackend.mapper.AdminMapper;
import com.example.xunibibackend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(String username, String password) {
        Admin admin = adminMapper.findByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        throw new RuntimeException("用户名或密码错误");
    }

    @Override
    public Admin createAdmin(String username, String password) {
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        adminMapper.createAdmin(admin);
        return admin;
    }

    @Override
    public Admin updateAdmin(int adminId, String username, String password) {
        Admin admin = new Admin();
        admin.setAdminId(adminId);
        admin.setUsername(username);
        admin.setPassword(password);
        int rows = adminMapper.updateAdmin(admin);
        if (rows > 0) {
            return admin;
        }
        throw new RuntimeException("更新管理员失败");
    }

    @Override
    public boolean deleteAdmin(int adminId) {
        return adminMapper.deleteAdmin(adminId) > 0;
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminMapper.findAllAdmins();
    }
}

