package com.example.xunibibackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Workstation {
    @Id
    private Integer id;

    private String type;

    private Integer isAvailable;

    private Integer rentedTeamId;

    private Double coinConsumption;
}
