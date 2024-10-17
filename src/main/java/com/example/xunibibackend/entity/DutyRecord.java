package com.example.xunibibackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class DutyRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "duty_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dutyDate;

    @Column(name = "virtual_coin_awarded",nullable = false)
    private Float coin_awarded;

    @Column(name = "description")
    private String description;
}
