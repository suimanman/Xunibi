package com.example.xunibibackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
@TableName("notice")
public class Notice {
    @Id
    private Integer id;
    private String content;
    private boolean display;
}
