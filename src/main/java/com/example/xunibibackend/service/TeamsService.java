package com.example.xunibibackend.service;

import com.example.xunibibackend.entity.Team;

import java.util.List;

public interface TeamsService {
    Integer createTeam(Team team);

    List<Team> getAllTeams();

    Team getTeamById(Integer id);

    Integer updateTeam(Integer id, Team team);

    void deleteTeamById(Integer id);
}
