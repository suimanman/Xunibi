package com.example.xunibibackend.service.impl;

import com.example.xunibibackend.entity.Team;
import com.example.xunibibackend.mapper.TeamMapper;
import com.example.xunibibackend.service.TeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Component
public class TeamsServiceImpl implements TeamsService {
    @Autowired
    TeamMapper teamMapper;
    @Override
    public Integer createTeam(Team team) {
        String name=team.getTeamName();
        Integer exist=teamMapper.selectName(name);
        if(exist.equals(0)) {
            team.setCreationDate(LocalDate.now());
            team.setVirtualCoins(500.0);
            return teamMapper.insert(team);
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
    public void deleteTeamById(Integer id) {
        teamMapper.deleteTeamById(id);
    }
}
