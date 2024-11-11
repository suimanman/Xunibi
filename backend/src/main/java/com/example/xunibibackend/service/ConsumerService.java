package com.example.xunibibackend.service;

import com.example.xunibibackend.entity.dto.RentalRequest;
import com.example.xunibibackend.entity.dto.ReturnRequest;
import com.example.xunibibackend.response.MyResult;
import org.springframework.stereotype.Service;

@Service
public interface ConsumerService {
    boolean rentEquipment(RentalRequest rentalRequest);

    boolean returnEquipment(ReturnRequest returnRequest);

    MyResult getList(String type);
}
