package com.example.xunibibackend.service;

import com.example.xunibibackend.response.MyResult;
import org.springframework.stereotype.Service;

@Service
public interface SignInCountService {

    MyResult  getTodaySignInCount();;


}
