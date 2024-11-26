package com.example.xunibibackend.controller;

import com.example.xunibibackend.entity.Admin;
import com.example.xunibibackend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public Admin login(@RequestParam String username, @RequestParam String password) {
        return adminService.login(username, password);
    }

    @PostMapping("/create")
    public Admin createAdmin(@RequestParam String username, @RequestParam String password) {
        return adminService.createAdmin(username, password);
    }

    @PutMapping("/update/{id}")
    public Admin updateAdmin(@PathVariable("id") int adminId,
                             @RequestParam String username,
                             @RequestParam String password) {
        return adminService.updateAdmin(adminId, username, password);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAdmin(@PathVariable("id") int adminId) {
        boolean success = adminService.deleteAdmin(adminId);
        return success ? "删除成功" : "删除失败";
    }

    @GetMapping("/list")
    public List<Admin> listAdmins() {
        return adminService.getAllAdmins();
    }
}
