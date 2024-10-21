package com.example.xunibibackend.controller;

import com.example.xunibibackend.entity.dto.RentalRequest;
import com.example.xunibibackend.entity.dto.ReturnRequest;
import com.example.xunibibackend.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;

    @PostMapping("/rent")
    public ResponseEntity<String> rentEquipment(@RequestBody RentalRequest rentalRequest) {
        boolean success = consumerService.rentEquipment(rentalRequest);
        if (success) {
            return ResponseEntity.ok("Equipment rented successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to rent equipment.");
        }
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnEquipment(@RequestBody ReturnRequest returnRequest) {
        boolean success = consumerService.returnEquipment(returnRequest);
        if (success) {
            return ResponseEntity.ok("Equipment returned successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to return equipment.");
        }
    }
}
