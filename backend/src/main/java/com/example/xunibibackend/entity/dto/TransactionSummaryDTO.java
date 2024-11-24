package com.example.xunibibackend.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionSummaryDTO {

    private String month;
    private String description;
    private int transactionCount;
    private BigDecimal totalExpenditure;

}
