package com.example.xunibibackend.service.impl;

import com.example.xunibibackend.mapper.AchievementMapper;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AchievementServiceImpl implements AchievementService {

    @Autowired
    private AchievementMapper achievementMapper;

    @Override
    public MyResult getAllAchievement() {
        return MyResult.success(achievementMapper.getAllAchievement());
    }
}
