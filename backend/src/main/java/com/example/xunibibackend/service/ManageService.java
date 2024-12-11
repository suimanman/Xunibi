package com.example.xunibibackend.service;

import com.example.xunibibackend.entity.Notice;
import com.example.xunibibackend.entity.VirtualCoinTransaction;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ManageService {
    List<String> getNotice();

    Integer insertNotice(String content);

    List<VirtualCoinTransaction> getRecords(HttpSession session);
}
