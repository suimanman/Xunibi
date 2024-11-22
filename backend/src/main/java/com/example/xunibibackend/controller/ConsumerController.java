package com.example.xunibibackend.controller;

import com.example.xunibibackend.entity.dto.RentalRequest;
import com.example.xunibibackend.entity.dto.ReturnRequest;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.ConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consumer")
@Slf4j
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;

    /*
        获取资源列表
     */
    @GetMapping("/list")
    public MyResult getList(@RequestParam String type){
        return consumerService.getList(type);
    }

    /*
        获取团队所租用的资源
     */
    @GetMapping("/rentList")
    public MyResult getRentList(@RequestParam Integer teamId){
        return consumerService.getRentList(teamId);
    }
    /*
        用户租用
        RentalRequest:  entity/dto/RentalRequest
     */

    @PostMapping("/rent")
    public MyResult rentEquipment(@RequestBody RentalRequest rentalRequest) {
//        log.info("租用：{}",rentalRequest);
        boolean success = consumerService.rentEquipment(rentalRequest);
        if (success) {
            return MyResult.success("租用成功！");
        } else {
            return MyResult.error("虚拟币不足，租用失败！");
        }
    }

    @PostMapping("/return")
    public MyResult returnEquipment(@RequestBody ReturnRequest returnRequest) {
//        log.info("归还中。。。");
        boolean success = consumerService.returnEquipment(returnRequest);
        if (success) {
            return MyResult.success("归还成功！");
        } else {
            return MyResult.success("超时归还！双倍扣除虚拟币");
        }
    }
}
