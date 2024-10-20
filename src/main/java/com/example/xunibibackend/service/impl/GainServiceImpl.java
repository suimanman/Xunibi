package com.example.xunibibackend.service.impl;

import com.example.xunibibackend.entity.Achievement;
import com.example.xunibibackend.entity.DutyRecord;
import com.example.xunibibackend.entity.Team;
import com.example.xunibibackend.mapper.AchievementMapper;
import com.example.xunibibackend.mapper.DutyMapper;
import com.example.xunibibackend.mapper.GainMapper;
import com.example.xunibibackend.mapper.TeamMapper;
import com.example.xunibibackend.service.GainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class GainServiceImpl implements GainService {

    @Autowired
    GainMapper gainMapper;
    @Autowired
    TeamMapper teamMapper;
    @Autowired
    AchievementMapper achievementMapper;
    @Autowired
    DutyMapper dutyMapper;
    @Override
    public boolean checkIn(Integer id) {
        return false;
    }

    @Override
    public boolean rewardAchievement(Achievement achievement) {
        Map<String,Double> map=new HashMap<>();
        map.put("paper",100.0);map.put("patent",200.0);map.put("competition",150.0);
        Integer teamId=achievement.getTeamId();
        Team team =teamMapper.selectByTeamId(teamId);
        // 获取 achievement.getType() 对应的虚拟币数量
        Double achievementCoin = map.get(achievement.getAchievementType());
        // 确保 achievementCoin 不为 null，避免空指针异常
        if (achievementCoin != null) {
            Double coinNew = team.getVirtualCoins() + achievementCoin;
            // 更新团队虚拟币
            teamMapper.updateCoinById(teamId,coinNew);
        } else {
            // achievement.getType() 不在 map 中
            System.out.println("Achievement type not found in map.");
        }
        achievement.setAchievementDate(LocalDate.now());
        achievement.setCoinAwarded(achievementCoin);
        achievementMapper.insert(achievement);
        return true;
    }

    @Override
    public boolean rewardDuty(DutyRecord dutyRecord) {
        Integer teamId=dutyRecord.getTeamId();
        Team team =teamMapper.selectByTeamId(teamId);
        Double coinNew=team.getVirtualCoins() + 20.0;
        teamMapper.updateCoinById(teamId,coinNew);
        dutyRecord.setDutyDate(LocalDate.now());
        dutyRecord.setCoinAwarded(20.0);
        dutyMapper.insert(dutyRecord);
        return true;
    }

    @Override
    public boolean rewardTraining(Integer id) {
        return false;
    }
}
