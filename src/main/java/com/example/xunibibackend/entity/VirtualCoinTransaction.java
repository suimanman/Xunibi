package com.example.xunibibackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class VirtualCoinTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    @Column(name = "virtual_coin_amount", nullable = false)
    private Float amount;

    @Column(name = "transaction_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    @Column(name = "description")
    private String description;
}
