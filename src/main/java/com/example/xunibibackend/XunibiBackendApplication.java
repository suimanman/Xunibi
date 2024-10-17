package com.example.xunibibackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.xunibibackend.mapper")
public class XunibiBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(XunibiBackendApplication.class, args);
    }

}
