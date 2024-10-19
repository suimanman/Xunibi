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
    private Integer id;

    @ManyToOne
    @Column(name = "team_id",nullable = false)
    private Team teamId;

    @ManyToOne
    @Column(name = "user_id",nullable = false)
    private User userId;

    private String eventName;
    private LocalDate eventDate;
    private String eventType;
    @Column(name="virtual_coins",nullable = false)
    private Double coins;
    private String description;

}
