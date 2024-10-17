package com.example.xunibibackend.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@TableName("team")
public class Team {
    @Id
    @TableId(value = "team_id")
    private Long teamId;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @Column(name = "virtual_coins", nullable = false)
    private Integer virtualCoins;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;
}
