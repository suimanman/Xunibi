package com.example.xunibibackend.controller;

import com.example.xunibibackend.entity.dto.RentalRequest;
import com.example.xunibibackend.entity.dto.ReturnRequest;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.ConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public MyResult rentEquipment(@RequestBody RentalRequest rentalRequest) {
        boolean success = consumerService.rentEquipment(rentalRequest);
        if (success) {
            return MyResult.success("租用成功！");
        } else {
            return MyResult.error("租用失败！");
        }
    }

    @PostMapping("/return")
    public MyResult returnEquipment(@RequestBody ReturnRequest returnRequest) {
        boolean success = consumerService.returnEquipment(returnRequest);
        if (success) {
            return MyResult.success("归还成功！");
        } else {
            return MyResult.error("归还失败！");
        }
    }
}
