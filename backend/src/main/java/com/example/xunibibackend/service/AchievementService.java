package com.example.xunibibackend.service;

import com.example.xunibibackend.entity.dto.AchievementByUserDTO;
import com.example.xunibibackend.response.MyResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AchievementService {
    MyResult getAllAchievement();
    List<AchievementByUserDTO> getAchievementByStu();
    // 导出数据为excel
    byte[] exportAchievementsToExcel() ;
}
