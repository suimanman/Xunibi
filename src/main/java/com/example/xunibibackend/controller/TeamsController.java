package com.example.xunibibackend.controller;

import com.example.xunibibackend.entity.Team;
import com.example.xunibibackend.service.TeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamsController {

    @Autowired
    private TeamsService teamsService;

    // 创建新团队
    @PostMapping("/create")
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        Team newTeam = teamsService.createTeam(team);
        return ResponseEntity.ok(newTeam);
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
