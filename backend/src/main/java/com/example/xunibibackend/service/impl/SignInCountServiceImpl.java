package com.example.xunibibackend.service.impl;

import com.example.xunibibackend.mapper.RentalRecordMapper;
import com.example.xunibibackend.mapper.SignRecordMapper;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.RecordService;
import com.example.xunibibackend.service.SignInCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignInCountServiceImpl implements SignInCountService {
    @Autowired

    private SignRecordMapper signRecordMapper;

    @Override
    public MyResult getTodaySignInCount() {
        return MyResult.success(signRecordMapper.getTodaySignInCount());
    }
}
