package com.example.xunibibackend.service.impl;

import com.example.xunibibackend.entity.Equipment;
import com.example.xunibibackend.mapper.EquipmentMapper;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.EquipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    EquipmentMapper equipmentMapper;

    // 查询所有工位
    public MyResult getAllEquipments() {
        List<Equipment> Equipments = equipmentMapper.getAllEquipments();
        return MyResult.success(Equipments); // 返回查询结果
    }

    // 新增工位
    public MyResult addEquipment(Equipment item) {
        int result = equipmentMapper.insert(item);
        if (result > 0) {
            return MyResult.success("工位新增成功");
        } else {
            return MyResult.error("工位新增失败");
        }
    }

    // 删除工位
    public MyResult deleteEquipmentById(int id) {
        int result = equipmentMapper.deleteById(id);
        if (result > 0) {
            return MyResult.success("工位删除成功");
        } else {
            return MyResult.error("工位删除失败");
        }
    }

    @Override
    public MyResult updateEquipment(int id,Equipment item) {
       // 检查工位是否存在
    Equipment existingEquipment = equipmentMapper.findById(item.getId());
    if (existingEquipment == null) {
        return MyResult.error("工位不存在");
    }

    // 执行更新操作
    int result = equipmentMapper.updateByadmin(id,item);
    if (result > 0) {
        return MyResult.success("工位更新成功");
    } else {
        return MyResult.error("工位更新失败");
    }
    }

    @Override
    public MyResult getEquipmentById(int id) {
        return MyResult.success( equipmentMapper.findById(id));
    }

    @Override
    public MyResult getAEquipmentsByName(String name) {
        return MyResult.success(equipmentMapper.findByName(name));
    }


}
