package com.example.xunibibackend.controller;

import com.example.xunibibackend.entity.Achievement;
import com.example.xunibibackend.entity.DutyRecord;
import com.example.xunibibackend.entity.SignInRecord;
import com.example.xunibibackend.entity.TrainRecord;
import com.example.xunibibackend.response.XunibiResult;
import com.example.xunibibackend.service.GainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gain")
public class GainController {
    @Autowired
    private GainService gainService;

    // a) 团队成员每日通过系统进行人脸识别打卡签到，获得定额虚拟币
    @PostMapping("/check-in")
    public XunibiResult checkIn(@RequestBody SignInRecord signInRecord) {
        boolean success = gainService.checkIn(signInRecord.getId());
        if (success) {
            return XunibiResult.success("签到成功，虚拟币已发放");
        } else {
            return XunibiResult.error("签到失败");
        }
    }

    // b) 通过论文专利、竞赛奖励等成果获得虚拟币
    @PostMapping("/reward/achievement")
    public XunibiResult rewardAchievement(@RequestBody Achievement achievement) {
        boolean success = gainService.rewardAchievement(achievement);
        if (success) {
            return XunibiResult.success("奖励虚拟币发放成功");
        } else {
            return XunibiResult.error("发放失败");
        }
    }

    // c) 通过值班工作获得虚拟币
    @PostMapping("/reward/duty")
    public XunibiResult rewardDuty(@RequestBody DutyRecord dutyRecord) {
        boolean success = gainService.rewardDuty(dutyRecord);
        if (success) {
            return XunibiResult.success("值班虚拟币发放成功");
        } else {
            return XunibiResult.error("发放失败");
        }
    }

    // d) 通过参加培训、会议获得虚拟币
    @PostMapping("/reward/training")
    public XunibiResult rewardTraining(@RequestBody TrainRecord trainRecord) {
        boolean success = gainService.rewardTraining(trainRecord);
        if (success) {
            return XunibiResult.success("培训或会议虚拟币发放成功");
        } else {
            return XunibiResult.error("发放失败");
        }
    }
}
