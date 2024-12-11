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

    private LocalDate dutyDate;

    private Double coinAwarded;

    private String image;

    private String status;

    private String description;
}
