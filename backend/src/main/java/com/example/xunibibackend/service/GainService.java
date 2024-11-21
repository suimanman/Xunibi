package com.example.xunibibackend.service;

import com.example.xunibibackend.entity.Achievement;
import com.example.xunibibackend.entity.DutyRecord;
import com.example.xunibibackend.entity.TrainRecord;
import com.example.xunibibackend.entity.dto.AchievementRequest;
import com.example.xunibibackend.response.MyResult;
import org.springframework.stereotype.Service;

@Service
public interface GainService {

    boolean checkIn(Integer id);

    boolean rewardAchievement(Achievement achievement);

    boolean rewardDuty(DutyRecord dutyRecord);

    boolean rewardTraining(TrainRecord trainRecord);

    MyResult submitAchievement(AchievementRequest achievementRequest);

    MyResult getAchievementList();
}
