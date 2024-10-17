package com.example.xunibibackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class SignInRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "sign_in_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date signInTime;

    @Column(name = "virtual_coin_awarded", nullable = false)
    private Float coin_awarded;
}
