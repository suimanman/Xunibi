package com.example.xunibibackend.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@TableName("team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teamId;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @Column(name = "virtual_coins")
    private Double virtualCoins;

    @Column(name = "creation_date")
    private LocalDate creationDate;
}
