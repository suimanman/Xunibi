package com.example.xunibibackend.service.impl;

import com.example.xunibibackend.entity.Achievement;
import com.example.xunibibackend.mapper.GainMapper;
import com.example.xunibibackend.service.GainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GainServiceImpl implements GainService {

    @Autowired
    GainMapper gainMapper;
    @Override
    public boolean checkIn(Long id) {
        return false;
    }

    @Override
    public boolean rewardAchievement(Achievement achievement) {
        return false;
    }

    @Override
    public boolean rewardDuty(Long id) {
        return false;
    }

    @Override
    public boolean rewardTraining(Integer id) {
        return false;
    }
}
