package com.example.xunibibackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class DeviceUsageRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Column(name = "device_name", nullable = false)
    private String deviceName;

    @Column(name = "usage_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date usageDate;

    @Column(name = "virtual_coin_spent", nullable = false)
    private Float coin_spent;
}
