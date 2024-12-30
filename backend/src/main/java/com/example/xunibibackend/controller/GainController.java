package com.example.xunibibackend.controller;

import com.example.xunibibackend.entity.Achievement;
import com.example.xunibibackend.entity.DutyRecord;
import com.example.xunibibackend.entity.dto.AchievementRequest;
import com.example.xunibibackend.entity.dto.DutyRequest;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.GainService;
import com.example.xunibibackend.service.impl.CheckInSchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/gain")
@CrossOrigin(
        origins = "http://localhost:8081", // 允许的前端地址
        allowCredentials = "true" // 允许携带凭证
)
@Slf4j
public class GainController {
    @Autowired
    private GainService gainService;
    @Autowired
    private CheckInSchedulerService checkInSchedulerService;

    //值日情况提交
    @PostMapping("/dutySubmit")
    public MyResult submitDuty(@RequestBody DutyRequest dutyRequest){
//        log.info("值班情况提交：{}",dutyRequest);
        return gainService.submitDuty(dutyRequest);
    }
    //成果提交待审核
    @PostMapping("/achievementSubmit")
    public MyResult submitAchievement(@RequestBody AchievementRequest achievementRequest){
        log.info("成果：{}",achievementRequest);
        return gainService.submitAchievement(achievementRequest);
    }

    //获得待审核列表
    @GetMapping("/achievementList")
    public MyResult getAchievementList(){
        return gainService.getAchievementList();
    }
        //获得待审核列表
    @GetMapping("/achievementListById/{id}")
    public MyResult getAchievementList(@PathVariable Integer id){
        return gainService.getAchievementListById(id);
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
    @PostMapping("/init")
    public ResponseEntity<String> initializeCheckIn() {
        checkInSchedulerService.initializeCheckInData();
        return ResponseEntity.ok("考勤数据初始化完成");
    }
}
