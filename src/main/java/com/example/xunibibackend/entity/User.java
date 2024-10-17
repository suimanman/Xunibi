package com.example.xunibibackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "role", nullable = false)
    private String role;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Column(name = "registration_date", nullable = false)
    private Date date;
}
