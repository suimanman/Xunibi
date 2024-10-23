package com.example.xunibibackend.service;

import com.example.xunibibackend.entity.Achievement;
import com.example.xunibibackend.entity.DutyRecord;
import com.example.xunibibackend.entity.TrainRecord;

public interface GainService {

    boolean checkIn(Integer id);

    boolean rewardAchievement(Achievement achievement);

    boolean rewardDuty(DutyRecord dutyRecord);

    boolean rewardTraining(TrainRecord trainRecord);
}
