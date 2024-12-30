package com.example.xunibibackend.service.impl;

import com.example.xunibibackend.entity.*;
import com.example.xunibibackend.entity.dto.*;
import com.example.xunibibackend.mapper.*;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.GainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class GainServiceImpl implements GainService {

    @Autowired
    CoinTransactionMapper coinTransactionMapper;

    @Autowired
    GainMapper gainMapper;
    @Autowired
    TeamMapper teamMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    AchievementMapper achievementMapper;
    @Autowired
    DutyMapper dutyMapper;

    @Override
    public boolean rewardAchievement(Achievement achievement) {
        Integer teamId=achievement.getTeamId();
        Team team =teamMapper.selectByTeamId(teamId);
        // 确保 achievementCoin 不为 null，避免空指针异常
        if (achievement.getCoinAwarded() != null) {
            Double coinNew = team.getVirtualCoins() + achievement.getCoinAwarded();
            // 更新团队虚拟币
            teamMapper.updateCoinById(teamId,coinNew);
        }
        achievementMapper.updateCoinAndStatus(achievement);

        //将记录添加到虚拟币交易记录表中
        VirtualCoinTransaction coinTransaction=new VirtualCoinTransaction();
        coinTransaction.setCoinAmount(achievement.getCoinAwarded());
        coinTransaction.setTransactionDate(LocalDate.now());
        coinTransaction.setTransactionType("收入");
        coinTransaction.setDescription("提交"+achievement.getAchievementType());
        coinTransaction.setTeamId(teamId);
        coinTransactionMapper.insert(coinTransaction);
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

        //将记录添加到虚拟币交易记录表中
        VirtualCoinTransaction coinTransaction=new VirtualCoinTransaction();
        coinTransaction.setCoinAmount(20.0);
        coinTransaction.setTransactionDate(LocalDate.now());
        coinTransaction.setTransactionType("收入");
        coinTransaction.setDescription("");
        coinTransaction.setTeamId(teamId);
        coinTransactionMapper.insert(coinTransaction);
        return true;
    }

    @Override
    public MyResult submitAchievement(AchievementRequest achievementRequest) {
        StudentInfo studentInfo = achievementRequest.getStudentInfo();
        AchievementInfo achievementInfo = achievementRequest.getAchievementInfo();
        StatementInfo statementInfo = achievementRequest.getStatementInfo();

        String userName = studentInfo.getUsername();
        Integer userId = userMapper.getUserIdByUsername(userName);
        Integer teamId = userMapper.getTeamIdByUsername(userName);

        Achievement achievement = new Achievement();
        achievement.setTeamId(teamId);
        achievement.setUserId(userId);
        achievement.setAchievementType(achievementInfo.getType());
        achievement.setAchievementName(achievementInfo.getName());
        achievement.setAchievementDate(LocalDate.parse(achievementInfo.getDateValue()));
        achievement.setCoinAwarded(0.0);
        String description = "学生基本信息:\n" +
                "姓名："+studentInfo.getName()+"，学号："+studentInfo.getUsername()+
                "，学院："+studentInfo.getDepartment()+"，专业："+studentInfo.getMajor()+"，班级："+studentInfo.getClazz()
                +"，所属团队："+studentInfo.getTeamName()
                + "\n成果基本信息：\n" + "成果名称："+achievementInfo.getName()+
                "，获得日期"+achievementInfo.getDateValue()+"，奖励单位："+achievementInfo.getAwardUnit()
                + "\n申请陈述：\n" + statementInfo.getInfo();
        achievement.setDescription(description);
        System.out.println(description);
        achievement.setStatus("待审核");
        achievementMapper.insert(achievement);
        return MyResult.success();
    }

    @Override
    public MyResult getAchievementList() {
        return MyResult.success(achievementMapper.getList());
    }


    @Override
    public MyResult getAchievementListById(Integer teamId) {
        return MyResult.success(achievementMapper.getListById(teamId));
    }

    @Override
    public MyResult submitDuty(DutyRequest dutyRequest) {
        if (dutyRequest == null ||
                dutyRequest.getName() == null || dutyRequest.getName().isEmpty() ||
                dutyRequest.getUsername() == null || dutyRequest.getUsername().isEmpty() ||
                dutyRequest.getTeamName() == null || dutyRequest.getTeamName().isEmpty() ||
                dutyRequest.getDescription() == null || dutyRequest.getDescription().isEmpty() ||
                dutyRequest.getImage() == null || dutyRequest.getImage().isEmpty()) {
            return MyResult.error("提交失败，信息不能为空！");
        }
        DutyRecord dutyRecord=new DutyRecord();
        User user=userMapper.getByUsername(dutyRequest.getUsername());
//        log.info("zhiban :{}",dutyRequest);
        dutyRecord.setTeamId(user.getTeamId());
        dutyRecord.setUserId(user.getUserId());
        dutyRecord.setDescription(dutyRequest.getTeamName()+"团队成员"+dutyRequest.getName()+"值班情况："+dutyRequest.getDescription());
        dutyRecord.setDutyDate(LocalDate.now());
        dutyRecord.setImage(dutyRequest.getImage());
        dutyRecord.setCoinAwarded(0.0);
        dutyRecord.setStatus("待审核");
        dutyMapper.insert(dutyRecord);
        return MyResult.success("提交成功！");
    }
}