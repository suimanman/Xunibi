package com.example.xunibibackend.entity.dto;

import com.example.xunibibackend.entity.Area;
import com.example.xunibibackend.entity.Camera;
import com.example.xunibibackend.entity.Equipment;
import com.example.xunibibackend.entity.Workstation;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TeamRent {

    private Integer id;

    private String type;

    private Integer isAvailable;

    private Integer rentedTeamId;

    private String rentTeamName;

    private Double coinConsumption;

    private String imageUrl;

    private LocalDate rentalDate;

    private LocalDate returnDate;

    // Area 构造函数
    public TeamRent(Area area) {
        this.id = area.getId();
        this.type = area.getType();
        this.isAvailable = area.getIsAvailable();
        this.rentedTeamId = area.getRentedTeamId();
        this.rentTeamName = area.getRentTeamName();
        this.coinConsumption = area.getCoinConsumption();
        this.imageUrl = area.getImageUrl();
        this.rentalDate = area.getRentalDate();
        this.returnDate = area.getReturnDate();
    }

    // Workstation 构造函数
    public TeamRent(Workstation workstation) {
        this.id = workstation.getId();
        this.type = workstation.getType();
        this.isAvailable = workstation.getIsAvailable();
        this.rentedTeamId = workstation.getRentedTeamId();
        this.rentTeamName = workstation.getRentTeamName();
        this.coinConsumption = workstation.getCoinConsumption();
        this.imageUrl = workstation.getImageUrl();
        this.rentalDate = workstation.getRentalDate();
        this.returnDate = workstation.getReturnDate();
    }

    // Equipment 构造函数
    public TeamRent(Equipment equipment) {
        this.id = equipment.getId();
        this.type = equipment.getType();
        this.isAvailable = equipment.getIsAvailable();
        this.rentedTeamId = equipment.getRentedTeamId();
        this.rentTeamName = equipment.getRentTeamName();
        this.coinConsumption = equipment.getCoinConsumption();
        this.imageUrl = equipment.getImageUrl();
        this.rentalDate = equipment.getRentalDate();
        this.returnDate = equipment.getReturnDate();
    }

    // Camera 构造函数
    public TeamRent(Camera camera) {
        this.id = camera.getId();
        this.type = camera.getType();
        this.isAvailable = camera.getIsAvailable();
        this.rentedTeamId = camera.getRentedTeamId();
        this.rentTeamName = camera.getRentTeamName();
        this.coinConsumption = camera.getCoinConsumption();
        this.imageUrl = camera.getImageUrl();
        this.rentalDate = camera.getRentalDate();
        this.returnDate = camera.getReturnDate();
    }
}
