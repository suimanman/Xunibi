package com.example.xunibibackend.service.impl;

import com.example.xunibibackend.entity.Team;
import com.example.xunibibackend.mapper.TeamMapper;
import com.example.xunibibackend.service.TeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamsServiceImpl implements TeamsService {
    @Autowired
    TeamMapper teamMapper;
    @Override
    public Team createTeam(Team team) {
        return null;
    }

    @Override
    public List<Team> getAllTeams() {
        return null;
    }

    @Override
    public Team getTeamById(Long id) {
        return teamMapper.selectById(id);
    }

    @Override
    public Team updateTeam(Long id, Team team) {
        return null;
    }

    @Override
    public void deleteTeam(Long id) {

    }
}
