package com.example.xunibibackend.entity.dto;

import lombok.Data;

@Data
public class DutyRequest {
    //学生姓名
    private String name;
    //学号
    private String id;
    //团队
    private String team;
    //值班情况
    private String description;
    //值日图片
    private String image;
}
