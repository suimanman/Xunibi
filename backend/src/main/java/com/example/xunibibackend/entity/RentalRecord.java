package com.example.xunibibackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@TableName("RentalRecords")
public class RentalRecord {
    @Id
    private Integer rentalId;
    private Integer teamId;
    private LocalDate rentalDate;
    private Double coinSpent;
    private Integer rentalDays;
    private String rentalType;
    //租用：1 或者 惩罚：0
    private boolean rentalOrReturn;
}
