package com.example.xunibibackend.controller;

import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.AchievementService;
import com.example.xunibibackend.service.RecordService;
import com.example.xunibibackend.service.VirtualCoinTransactionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/records")
@Slf4j
@CrossOrigin(
        origins = "http://localhost:8081", // 允许的前端地址
        allowCredentials = "true" // 允许携带凭证
)

public class RecordesController {

    @Autowired
    private VirtualCoinTransactionsService virtualCoinTransactionsService;
    @Autowired
    private AchievementService achievementService;

    @Autowired
    private RecordService recordService;

    // 交易记录
    @GetMapping("/virtualCoinRecords")
    public MyResult getAllVirtualCoinTransactions() {
        return virtualCoinTransactionsService.getAllRecords();
    }

    // 审核记录
    @GetMapping("/achieveRecords")
    public MyResult getAllAchievements() {
        return achievementService.getAllAchievement();
    }

    @GetMapping("/rentalRecords")
    // 租借记录
    public MyResult getAllRentalRecords() {
        return recordService.getAllRentalRecord();
    }



}
