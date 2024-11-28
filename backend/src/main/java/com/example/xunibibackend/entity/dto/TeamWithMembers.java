package com.example.xunibibackend.entity.dto;

import lombok.Data;

@Data
public class TeamWithMembers {
    private int teamId;
    private String teamName;
    private int teamMemberCount;
}
