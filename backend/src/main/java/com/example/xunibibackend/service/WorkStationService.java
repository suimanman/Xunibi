package com.example.xunibibackend.service;

import com.example.xunibibackend.entity.Workstation;
import com.example.xunibibackend.response.MyResult;
import org.springframework.stereotype.Service;

@Service
public interface WorkStationService {
    // 查看
    public MyResult getAllWorkstations() ;
    // 增加
    public MyResult addWorkstation(Workstation item);
    // 删除
    public MyResult deleteWorkstationById(int id);
    // 修改
    public MyResult updateWorkstation(int id,Workstation item);
    //通过名字查询
    public MyResult getWorkstationByName(String name);
}
