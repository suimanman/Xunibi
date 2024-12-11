package com.example.xunibibackend.service;

import com.example.xunibibackend.entity.Achievement;
import com.example.xunibibackend.entity.DutyRecord;
import com.example.xunibibackend.entity.dto.AchievementRequest;
import com.example.xunibibackend.entity.dto.DutyRequest;
import com.example.xunibibackend.response.MyResult;
import org.springframework.stereotype.Service;

@Service
public interface GainService {

    boolean rewardAchievement(Achievement achievement);

    boolean rewardDuty(DutyRecord dutyRecord);

    MyResult submitAchievement(AchievementRequest achievementRequest);

    MyResult getAchievementList();

    MyResult getAchievementListById(Integer id);

    MyResult submitDuty(DutyRequest dutyRequest);
}
