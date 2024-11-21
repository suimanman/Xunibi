package com.example.xunibibackend.controller;

import com.example.xunibibackend.entity.Achievement;
import com.example.xunibibackend.entity.DutyRecord;
import com.example.xunibibackend.entity.SignInRecord;
import com.example.xunibibackend.entity.TrainRecord;
import com.example.xunibibackend.entity.dto.AchievementRequest;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.GainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gain")
public class GainController {
    @Autowired
    private GainService gainService;

    // a) 团队成员每日通过系统进行人脸识别打卡签到，获得定额虚拟币
    @PostMapping("/check-in")
    public MyResult checkIn(@RequestBody SignInRecord signInRecord) {
        boolean success = gainService.checkIn(signInRecord.getId());
        if (success) {
            return MyResult.success("签到成功，虚拟币已发放");
        } else {
            return MyResult.error("签到失败");
        }
    }

    //成果提交待审核
    @PostMapping("/achievementSubmit")
    public MyResult submitAchievement(@RequestBody AchievementRequest achievementRequest){
        return gainService.submitAchievement(achievementRequest);
    }

    //获得待审核列表
    @GetMapping("/achievementList")
    public MyResult getAchievementList(){
        return gainService.getAchievementList();
    }
    // 管理员审核接口
    @PostMapping("/reward/achievement")
    public MyResult rewardAchievement(@RequestBody Achievement achievement) {
        boolean success = gainService.rewardAchievement(achievement);
        if (success) {
            return MyResult.success("奖励虚拟币发放成功");
        } else {
            return MyResult.error("发放失败");
        }
    }

    // c) 通过值班工作获得虚拟币
    @PostMapping("/reward/duty")
    public MyResult rewardDuty(@RequestBody DutyRecord dutyRecord) {
        boolean success = gainService.rewardDuty(dutyRecord);
        if (success) {
            return MyResult.success("值班虚拟币发放成功");
        } else {
            return MyResult.error("发放失败");
        }
    }

    // d) 通过参加培训、会议获得虚拟币
    @PostMapping("/reward/training")
    public MyResult rewardTraining(@RequestBody TrainRecord trainRecord) {
        boolean success = gainService.rewardTraining(trainRecord);
        if (success) {
            return MyResult.success("培训或会议虚拟币发放成功");
        } else {
            return MyResult.error("发放失败");
        }
    }
}
