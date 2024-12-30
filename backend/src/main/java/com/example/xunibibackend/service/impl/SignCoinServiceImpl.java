package com.example.xunibibackend.service.impl;

import com.example.xunibibackend.mapper.SignCoinMapper;
import com.example.xunibibackend.mapper.SignRecordMapper;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.SignCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignCoinServiceImpl implements SignCoinService {


    @Autowired
    private SignCoinMapper signCoinMapper;
    @Override
    public MyResult getSignCoin() {
        return MyResult.success(signCoinMapper.findCoin());
    }

    @Override
    public void updateSignCoin(double coin)    {
        if ( coin < 0) {
            throw new IllegalArgumentException("虚拟币数量必须为非负数！");
        }
        try {
            signCoinMapper.setCoin(coin);
        } catch (Exception e) {
            throw new RuntimeException("数据库更新失败！", e);
        }
    }
}
