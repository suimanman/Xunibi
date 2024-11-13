package com.example.xunibibackend.entity.dto;

import lombok.Data;

@Data
public class ReturnRequest {
    private String type1;
    private String type2;
    private Integer rentalDays;
    private Integer teamId;
}
