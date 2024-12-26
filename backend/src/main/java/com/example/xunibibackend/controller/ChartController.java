package com.example.xunibibackend.controller;


import com.example.xunibibackend.entity.Team;
import com.example.xunibibackend.entity.User;
import com.example.xunibibackend.entity.dto.TeamAchievementSummary;
import com.example.xunibibackend.entity.dto.TeamWithMembers;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.TeamsService;
import com.example.xunibibackend.service.UserService;
import com.example.xunibibackend.service.VirtualCoinTransactionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/chart")
@CrossOrigin(
        origins = "http://localhost:8081", // 允许的前端地址
        allowCredentials = "true" // 允许携带凭证
)
public class ChartController {
   @Autowired
   private TeamsService teamsService;
   @Autowired
   private UserService userService;
   @Autowired
   private VirtualCoinTransactionsService virtualCoinTransactionsService;
   @GetMapping("/teamnums")
   public MyResult getTeamsNums(){
       List<Team> allTeams = teamsService.getAllTeams();
        return MyResult.success(allTeams.size());
   }
   @GetMapping("/usernums")
   public MyResult getUserNums(){
       List<User> allUsers = userService.getAllUser();
       return MyResult.success(allUsers.size());
   }


    @GetMapping("/teamwithmeb")
    public MyResult getTeamsWithMemberCount() {
        List<TeamWithMembers> teams = teamsService.getTeamsWithMemberCount();
        return MyResult.success(teams);
    }

//    @GetMapping("/teamwithachive")
//     public MyResult getTeamAchievementSummaries(@RequestParam String month) {
//        List<TeamAchievementSummary> summaries = teamsService.getTeamAchievementSummaries(month);
//        return MyResult.success(summaries);
//    }

    @GetMapping("/teamwithachive")
    public MyResult getTeamAchievementSummaries(@RequestParam(defaultValue = "") String month) {
       log.info("month:", month);
    // 如果 month 为空，则查询所有数据
        if (month.isEmpty()) {
        // 处理返回所有数据的逻辑
        List<TeamAchievementSummary> summaries = teamsService.getTeamAchievementSummaries(null);  // 传递 null 或空值
        return MyResult.success(summaries);
     } else {
        // 如果传递了 month 参数，则根据指定月份查询
        List<TeamAchievementSummary> summaries = teamsService.getTeamAchievementSummaries(month);
        return MyResult.success(summaries);
     }
    }

    @GetMapping("/monwithspend")
    public MyResult getMonWithSpend() {
      return MyResult.success(virtualCoinTransactionsService.getMonthlyExpenditureSummary());
    }
    @GetMapping("/monwithearn")
    public MyResult getMonWithEarn() {
      return MyResult.success(virtualCoinTransactionsService.getMonthlyEarnSummary());
    }




}
