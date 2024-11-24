package com.example.xunibibackend.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamAchievementSummary {
    private int teamId;
    private String teamName;
    private int paperCount;
    private int competitionCount;
    private int otherCount;
}
