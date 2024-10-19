package com.example.xunibibackend.service;

import com.example.xunibibackend.entity.Achievement;

public interface GainService {

    boolean checkIn(Integer id);

    boolean rewardAchievement(Achievement achievement);

    boolean rewardDuty(Long id);

    boolean rewardTraining(Integer id);
}
