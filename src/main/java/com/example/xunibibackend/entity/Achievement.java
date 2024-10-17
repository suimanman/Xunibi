package com.example.xunibibackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "achievement_type", nullable = false)
    private String type;

    @Column(name = "virtual_coin_awarded", nullable = false)
    private Float coin_awarded;

    @Column(name = "description")
    private String description;

    @Column(name = "achievement_date", nullable = false)
    private Date date;
}
