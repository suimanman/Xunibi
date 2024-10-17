package com.example.xunibibackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @Column(name = "virtual_coins_balance", nullable = false)
    private Integer virtualCoins;

    @Column(name = "creation_date", nullable = false)
    private Date creation_date;
}
