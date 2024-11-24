package com.example.xunibibackend.controller;


import com.example.xunibibackend.entity.Team;
import com.example.xunibibackend.entity.User;
import com.example.xunibibackend.entity.dto.TeamAchievementSummary;
import com.example.xunibibackend.entity.dto.TeamWithMembers;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.TeamsService;
import com.example.xunibibackend.service.UserService;
import com.example.xunibibackend.service.VirtualCoinTransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chart")
@CrossOrigin
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

    @GetMapping("/teamwithachive")
     public MyResult getTeamAchievementSummaries() {
        List<TeamAchievementSummary> summaries = teamsService.getTeamAchievementSummaries();
        return MyResult.success(summaries);
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
