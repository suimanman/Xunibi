package com.example.xunibibackend.entity.dto;

import lombok.Data;

@Data
public class SignInData {
    private String user_id;
    private String ext_id;
    private String terminal_id;
    private String check_type;
    private Long check_time;
    private String check_data;
    private Integer id;
}
