package com.example.xunibibackend.controller;

import com.example.xunibibackend.entity.Team;
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
    public ResponseEntity<String> createTeam(@RequestBody Team team) {
        int exist=teamsService.createTeam(team);
        if(exist==1)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("团队名称已经被注册！");
    }

    // 获取所有团队列表
    @GetMapping("/list")
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamsService.getAllTeams();
        return ResponseEntity.ok(teams);
    }

    // 根据 ID 获取单个团队
    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        Team team = teamsService.getTeamById(id);
        return ResponseEntity.ok(team);
    }

    // 更新团队信息
    @PutMapping("/update/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody Team team) {
        Team updatedTeam = teamsService.updateTeam(id, team);
        return ResponseEntity.ok(updatedTeam);
    }

    // 删除团队
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamsService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
}
