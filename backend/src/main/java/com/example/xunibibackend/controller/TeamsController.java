package com.example.xunibibackend.controller;

import com.example.xunibibackend.entity.Team;
import com.example.xunibibackend.response.XunibiResult;
import com.example.xunibibackend.service.TeamsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public XunibiResult createTeam(@RequestBody Team team) {
        int exist=teamsService.createTeam(team);
        if(exist==1)
            return XunibiResult.success();
        else
            return XunibiResult.error("团队名称已经被注册！");
    }

    // 获取所有团队列表
    @GetMapping("/list")
    public XunibiResult getAllTeams() {
        List<Team> teams = teamsService.getAllTeams();
        return XunibiResult.success(teams);
    }

    // 根据 ID 获取单个团队
    @GetMapping("/{id}")
    public XunibiResult getTeamById(@PathVariable Integer id) {
        Team team = teamsService.getTeamById(id);
        return XunibiResult.success(team);
    }

    // 更新团队信息
    @PutMapping("/update/{id}")
    public XunibiResult updateTeam(@PathVariable Integer id, @RequestBody Team team) {
        Integer updatedTeam = teamsService.updateTeam(id, team);
        return XunibiResult.success("更新成功！");
    }

    // 删除团队
    @DeleteMapping("/delete/{id}")
    public XunibiResult deleteTeam(@PathVariable Integer id) {
        teamsService.deleteTeamById(id);
        return XunibiResult.success();
    }
}
