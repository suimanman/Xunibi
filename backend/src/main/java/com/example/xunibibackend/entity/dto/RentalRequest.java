package com.example.xunibibackend.entity.dto;

import lombok.Data;

@Data
public class RentalRequest {
    //1:工位  2:固定设备  3:摄像设备  4:场地
    private String type1;
    //具体类型
    private String type2;
    //租借天数
    private Integer rentalDays;
    //租借团队
    private Integer teamId;

}
