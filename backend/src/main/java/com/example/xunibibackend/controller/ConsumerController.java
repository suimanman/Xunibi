package com.example.xunibibackend.controller;

import com.example.xunibibackend.entity.dto.RentalRequest;
import com.example.xunibibackend.entity.dto.ReturnRequest;
import com.example.xunibibackend.response.XunibiResult;
import com.example.xunibibackend.service.ConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
@Slf4j
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;

    @PostMapping("/rent")
    public XunibiResult rentEquipment(@RequestBody RentalRequest rentalRequest) {
        boolean success = consumerService.rentEquipment(rentalRequest);
        if (success) {
            return XunibiResult.success("租用成功！");
        } else {
            return XunibiResult.error("租用失败！");
        }
    }

    @PostMapping("/return")
    public XunibiResult returnEquipment(@RequestBody ReturnRequest returnRequest) {
        boolean success = consumerService.returnEquipment(returnRequest);
        if (success) {
            return XunibiResult.success("归还成功！");
        } else {
            return XunibiResult.error("归还失败！");
        }
    }
}
