package com.example.xunibibackend.service.impl;

import com.example.xunibibackend.entity.*;
import com.example.xunibibackend.entity.dto.AchievementInfo;
import com.example.xunibibackend.entity.dto.AchievementRequest;
import com.example.xunibibackend.entity.dto.StatementInfo;
import com.example.xunibibackend.entity.dto.StudentInfo;
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
    @Autowired
    TrainMapper trainMapper;
    @Override
    public boolean checkIn(Integer id) {
        return false;
    }

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
        coinTransaction.setDescription(dutyRecord.getDescription());
        coinTransaction.setTeamId(teamId);
        coinTransactionMapper.insert(coinTransaction);
        return true;
    }

    @Override
    public boolean rewardTraining(TrainRecord trainRecord) {
        // 创建一个map，存储不同eventType对应的虚拟币奖励值
        Map<String, Double> map = new HashMap<>();
        map.put("校内培训", 50.0);
        map.put("会议", 30.0);
        map.put("校外培训", 70.0);

        Integer teamId = trainRecord.getTeamId();
        Team team = teamMapper.selectByTeamId(teamId);

        // 获取 trainRecord.getEventType() 对应的虚拟币数量
        Double trainingCoin = map.get(trainRecord.getEventType());

        // 确保 trainingCoin 不为 null，避免空指针异常
        if (trainingCoin != null) {
            Double coinNew = team.getVirtualCoins() + trainingCoin;
            // 更新团队虚拟币
            teamMapper.updateCoinById(teamId, coinNew);
            trainRecord.setCoins(trainingCoin);  // 设置虚拟币值
        } else {
            // trainRecord.getEventType() 不在 map 中
            System.out.println("Event type not found in map.");
            return false;
        }
        // 将trainRecord插入数据库
        trainMapper.insert(trainRecord);

        //将记录添加到虚拟币交易记录表中
        VirtualCoinTransaction coinTransaction=new VirtualCoinTransaction();
        coinTransaction.setCoinAmount(trainingCoin);
        coinTransaction.setTransactionDate(LocalDate.now());
        coinTransaction.setTransactionType("收入");
        coinTransaction.setDescription(trainRecord.getDescription());
        coinTransaction.setTeamId(teamId);
        coinTransactionMapper.insert(coinTransaction);
        return true;
    }

    @Override
    public MyResult submitAchievement(AchievementRequest achievementRequest) {
        StudentInfo studentInfo = achievementRequest.getStudentInfo();
        AchievementInfo achievementInfo = achievementRequest.getAchievementInfo();
        StatementInfo statementInfo = achievementRequest.getStatementInfo();

        String userName = studentInfo.getId();
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
                "姓名："+studentInfo.getName()+"，学号："+studentInfo.getId()+
                "，学院："+studentInfo.getDepartment()+"，专业："+studentInfo.getMajor()+"，班级："+studentInfo.getClazz()
                +"，所属团队："+studentInfo.getTeam()
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
}
