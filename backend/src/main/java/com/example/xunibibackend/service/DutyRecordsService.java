package com.example.xunibibackend.service;

import com.example.xunibibackend.entity.DutyRecord;
import com.example.xunibibackend.response.MyResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DutyRecordsService {
    MyResult getAllDutyRecords();
    MyResult getDutyRecordById(int dutyId);
    MyResult addDutyRecord(DutyRecord dutyRecords);
    MyResult updateDutyRecord(DutyRecord dutyRecords);
    MyResult deleteDutyRecord(int dutyId);


    MyResult getReviewDutyRecord();
    MyResult dutyReward(DutyRecord dutyRecord);
}
