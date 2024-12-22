package com.example.xunibibackend.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AchievementByUserDTO {
    private String studentName;
    private String department;
    private String major;
    private String clazz;
    private String teamName;
    private String achievementName;
    private String achievementType;
    private LocalDate achievementDate;
    private BigDecimal coinAwarded;
    private String description;
    private String status;

}
