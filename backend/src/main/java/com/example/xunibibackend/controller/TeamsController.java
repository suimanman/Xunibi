package com.example.xunibibackend.controller;

import com.example.xunibibackend.entity.Team;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.TeamsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
@Slf4j
public class TeamsController {

    @Autowired
    private TeamsService teamsService;

    // 创建新团队
    @PostMapping("/create")
    public MyResult createTeam(@RequestBody Team team) {
        int exist=teamsService.createTeam(team);
        if(exist==1)
            return MyResult.success();
        else
            return MyResult.error("团队名称已经被注册！");
    }

    // 获取所有团队列表
    @GetMapping("/list")
    public MyResult getAllTeams() {
        List<Team> teams = teamsService.getAllTeams();
        return MyResult.success(teams);
    }

    // 根据 ID 获取单个团队
    @GetMapping("/{id}")
    public MyResult getTeamById(@PathVariable Integer id) {
        Team team = teamsService.getTeamById(id);
        return MyResult.success(team);
    }

    // 更新团队信息
    @PutMapping("/update/{id}")
    public MyResult updateTeam(@PathVariable Integer id, @RequestBody Team team) {
        Integer updatedTeam = teamsService.updateTeam(id, team);
        return MyResult.success("更新成功！");
    }

    // 删除团队
    @DeleteMapping("/delete/{id}")
    public MyResult deleteTeam(@PathVariable Integer id) {
        teamsService.deleteTeamById(id);
        return MyResult.success();
    }
}
