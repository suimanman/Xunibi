package com.example.xunibibackend.controller;

import com.example.xunibibackend.entity.DutyRecord;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.DutyRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dutyrecords")
@CrossOrigin(
        origins = "http://localhost:8081", // 允许的前端地址
        allowCredentials = "true" // 允许携带凭证
)
public class DutyRecordsController {

    @Autowired
    private DutyRecordsService dutyRecordsService;
    /**
     * 获取所有值班记录
     */
    @GetMapping("/all")
    public MyResult getAllDutyRecords() {
        return dutyRecordsService.getAllDutyRecords();
    }

    @GetMapping("/reviewall")
    public MyResult getAllReviewRecords() {
        return dutyRecordsService.getReviewDutyRecord();
    }


    /**
     * 根据 ID 获取值班记录
     */
    @GetMapping("/{id}")
    public MyResult getDutyRecordById(@PathVariable("id") int dutyId) {
        return dutyRecordsService.getDutyRecordById(dutyId);
    }

    /**
     * 添加新的值班记录
     */
    @PostMapping("/add")
    public MyResult addDutyRecord(@RequestBody DutyRecord dutyRecord) {
        return dutyRecordsService.addDutyRecord(dutyRecord);
    }

    /**
     * 更新值班记录
     */
    @PutMapping("/update")
    public MyResult updateDutyRecord(@RequestBody DutyRecord dutyRecord) {
        return dutyRecordsService.updateDutyRecord(dutyRecord);
    }
    /**
     * 根据 ID 删除值班记录
     */
    @DeleteMapping("/delete/{id}")
    public MyResult deleteDutyRecord(@PathVariable("id") int dutyId) {
        return dutyRecordsService.deleteDutyRecord(dutyId);
    }

    @PostMapping("/reward")
    public MyResult dutyReward(@RequestBody DutyRecord dutyRecord)
    {
        return  dutyRecordsService.dutyReward(dutyRecord);
    }









}
