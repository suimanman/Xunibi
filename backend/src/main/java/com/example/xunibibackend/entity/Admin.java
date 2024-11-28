package com.example.xunibibackend.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Admin {
    private int adminId;
    private String username;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

