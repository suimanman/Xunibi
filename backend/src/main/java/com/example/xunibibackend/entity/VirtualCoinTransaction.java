package com.example.xunibibackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@TableName("VirtualCoinTransactions")
public class VirtualCoinTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private Integer teamId;

    private String transactionType;

    private Double coinAmount;

    private LocalDate transactionDate;

    private String description;
}
