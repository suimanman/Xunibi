package com.example.xunibibackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Workstation {
    @Id
    private Integer id;

    private String type;

    private Integer isAvailable;

    private Integer rentedTeamId;

    private String rentTeamName;

    private Double coinConsumption;

    private String imageUrl;

    private LocalDate rentalDate;

    private LocalDate returnDate;
}
