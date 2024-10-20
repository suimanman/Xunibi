package com.example.xunibibackend.service;

import com.example.xunibibackend.entity.Achievement;
import com.example.xunibibackend.entity.DutyRecord;

public interface GainService {

    boolean checkIn(Integer id);

    boolean rewardAchievement(Achievement achievement);

    boolean rewardDuty(DutyRecord dutyRecord);

    boolean rewardTraining(Integer id);
}
