package com.example.xunibibackend.service.impl;

import com.example.xunibibackend.controller.UserController;
import com.example.xunibibackend.entity.Team;
import com.example.xunibibackend.entity.User;
import com.example.xunibibackend.entity.dto.TeamAchievementSummary;
import com.example.xunibibackend.entity.dto.TeamWithMembers;
import com.example.xunibibackend.mapper.TeamMapper;
import com.example.xunibibackend.mapper.UserMapper;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.TeamsService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
public class TeamsServiceImpl implements TeamsService {
    @Autowired
    TeamMapper teamMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public Integer createTeam(Team team) {
        String name=team.getTeamName();
        Integer exist=teamMapper.selectName(name);
        if(exist.equals(0)) {
            return teamMapper.createTeamByAdmin(team);
        }
        else return 0;
    }

    @Override
    public List<Team> getAllTeams() {
        return teamMapper.selectList(null);
    }

    @Override
    public Team getTeamById(Integer id) {
        return teamMapper.selectByTeamId(id);
    }

    @Override
    public Integer updateTeam(Integer id, Team team) {
        return teamMapper.updateTeam(id,team);
    }

    @Override
    public MyResult deleteTeamById(Integer id) {
        if (userMapper.getUsersByTeamId(id) == null || userMapper.getUsersByTeamId(id).isEmpty()) {
            teamMapper.deleteTeamById(id);
            return MyResult.success("删除成功");
        }else {
            return MyResult.error("id为"+id +"的团队还有队员");
        }

    }

    @Override
    public Double getCoin(HttpSession session) {
        // 从session中取出用户信息
        User sessionUser=(User) session.getAttribute(UserController.SESSION_NAME);
//        log.info("虚拟币user{}",sessionUser);
        Integer teamId=userMapper.getTeamIdByUsername(sessionUser.getUsername());
        Team team=teamMapper.selectByTeamId(teamId);
        Double coin=teamMapper.getCoin(team);
        return coin;
    }


     public List<TeamWithMembers> getTeamsWithMemberCount() {
        return teamMapper.getTeamsWithMemberCount();
    }
     public List<TeamAchievementSummary> getTeamAchievementSummaries() {
        return teamMapper.getTeamAchievementSummaries();
    }

//    @Override
//    public int createTeamByAdmin(Team team) {
//        teamMapper.createTeamByAdmin(team);
//        return 0;
//    }
}
