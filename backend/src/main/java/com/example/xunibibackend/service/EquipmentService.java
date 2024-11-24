package com.example.xunibibackend.service;

import com.example.xunibibackend.entity.Equipment;
import com.example.xunibibackend.response.MyResult;
import org.springframework.stereotype.Service;

@Service
public interface EquipmentService {
    // 查看
    public MyResult getAllEquipments() ;
    // 增加
    public MyResult addEquipment(Equipment item);
    // 删除
    public MyResult deleteEquipmentById(int id);
    // 修改
    public MyResult updateEquipment(int id,Equipment item);
    //通过id查询
    public MyResult getEquipmentById(int id);
    //通过名字查询
    public MyResult getAEquipmentsByName(String name);
}
