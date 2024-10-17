package com.example.xunibibackend.service;

import com.example.xunibibackend.entity.Team;

import java.util.List;

public interface TeamsService {
    Team createTeam(Team team);

    List<Team> getAllTeams();

    Team getTeamById(Long id);

    Team updateTeam(Long id, Team team);

    void deleteTeam(Long id);
}
