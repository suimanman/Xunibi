package com.example.xunibibackend.service;

import org.springframework.stereotype.Service;

@Service
public interface ManageService {
    String getNotice();

    Integer insertNotice(String content);
}
