package com.example.xunibibackend.entity.dto;

import lombok.Data;

@Data
public class AchievementRequest {
    private StudentInfo studentInfo;
    private AchievementInfo achievementInfo;
    private StatementInfo statementInfo;
}
