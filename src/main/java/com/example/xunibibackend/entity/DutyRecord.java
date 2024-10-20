package com.example.xunibibackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@TableName("DutyRecords")
public class DutyRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dutyId;

    private Integer teamId;

    private Integer userId;

    @Column(name = "duty_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate dutyDate;

    @Column(name = "coin_awarded",nullable = false)
    private Double coinAwarded;

    @Column(name = "description")
    private String description;
}
