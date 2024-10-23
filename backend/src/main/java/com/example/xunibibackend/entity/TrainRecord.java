package com.example.xunibibackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@TableName("trainRecords")
public class TrainRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trainId;

    @Column(name = "team_id",nullable = false)
    private Integer teamId;

    @Column(name = "user_id",nullable = false)
    private Integer userId;

    private String eventName;
    private LocalDate eventDate;
    private String eventType;
    private Double coins;
    private String description;

}
