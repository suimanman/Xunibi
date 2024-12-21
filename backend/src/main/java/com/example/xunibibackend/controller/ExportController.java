package com.example.xunibibackend.controller;


import com.example.xunibibackend.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/export")
@CrossOrigin(
        origins = "http://localhost:8081", // 允许的前端地址
        allowCredentials = "true" // 允许携带凭证
)
public class ExportController {

    @Autowired
    private AchievementService achievementService;
    @GetMapping("/achieve")
    public ResponseEntity<byte[]> exportAchievements() {
        byte[] excelFile = achievementService.exportAchievementsToExcel();

        // 设置响应头，提示浏览器下载文件
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Achievements_Report.xlsx");

        return new ResponseEntity<>(excelFile, headers, HttpStatus.OK);
    }
}
