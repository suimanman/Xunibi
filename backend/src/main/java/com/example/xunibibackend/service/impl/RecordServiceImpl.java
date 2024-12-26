package com.example.xunibibackend.service.impl;

import com.example.xunibibackend.mapper.RecordMapper;
import com.example.xunibibackend.mapper.RentalRecordMapper;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired

    private RentalRecordMapper rentalRecordMapper;

    public MyResult getAllRentalRecord(){
        return MyResult.success(rentalRecordMapper.getAllRentalRecord());
    }
}
