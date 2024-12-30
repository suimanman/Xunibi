package com.example.xunibibackend.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInCountRecords {
    private Integer id;    // 主键，自增
    private LocalDate date;     // 签到日期
    private Integer count; // 签到人数
}
