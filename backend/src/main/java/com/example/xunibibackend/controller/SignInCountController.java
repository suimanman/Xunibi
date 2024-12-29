package com.example.xunibibackend.controller;

import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.SignInCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signincount")
@Slf4j
@CrossOrigin(
        origins = "http://localhost:8081", // 允许的前端地址
        allowCredentials = "true" // 允许携带凭证
)
public class SignInCountController {
    @Autowired
    private  SignInCountService signInCountService;

    @GetMapping("todycount")
    public MyResult getTodaySignInCount(){
        return signInCountService.getTodaySignInCount();
    }


}
