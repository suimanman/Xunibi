package com.example.xunibibackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String username;

    private String password;

    private String email;

    private String role;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team_id;

    @Column(name = "registration_date", nullable = false)
    private Date date;
}
