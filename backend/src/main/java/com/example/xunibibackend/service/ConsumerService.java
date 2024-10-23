package com.example.xunibibackend.service;

import com.example.xunibibackend.entity.dto.RentalRequest;
import com.example.xunibibackend.entity.dto.ReturnRequest;

public interface ConsumerService {
    boolean rentEquipment(RentalRequest rentalRequest);

    boolean returnEquipment(ReturnRequest returnRequest);
}
