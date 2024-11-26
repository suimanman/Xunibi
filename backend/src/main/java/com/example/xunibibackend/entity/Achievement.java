package com.example.xunibibackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@TableName("Achievements")
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer achievementId;

    @Column(name = "team_id", nullable = false)
    private Integer teamId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "achievement_type", nullable = false)
    private String achievementType;

    @Column(name = "achievement_name", nullable = false)
    private String achievementName;

    private Double coinAwarded;

    private String description;

    @Column(name = "achievement_date")
    private LocalDate achievementDate;
    //待审核，通过，未通过
    private String status;
}