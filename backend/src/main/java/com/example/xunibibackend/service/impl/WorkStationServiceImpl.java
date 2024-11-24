package com.example.xunibibackend.service.impl;

import com.example.xunibibackend.entity.Workstation;
import com.example.xunibibackend.mapper.WorkstationMapper;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.WorkStationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;
@Slf4j
@Component
public class WorkStationServiceImpl implements WorkStationService {

    @Autowired
    WorkstationMapper workstationMapper;

    // 查询所有工位
    public MyResult getAllWorkstations() {
        List<Workstation> workstations = workstationMapper.getAllWorkstations();
        return MyResult.success(workstations); // 返回查询结果
    }

    // 新增工位
    public MyResult addWorkstation(Workstation item) {
        int result = workstationMapper.insert(item);
        if (result > 0) {
            return MyResult.success("工位新增成功");
        } else {
            return MyResult.error("工位新增失败");
        }
    }

    // 删除工位
    public MyResult deleteWorkstationById(int id) {
        int result = workstationMapper.deleteById(id);
        if (result > 0) {
            return MyResult.success("工位删除成功");
        } else {
            return MyResult.error("工位删除失败");
        }
    }

    @Override
    public MyResult updateWorkstation(int id,Workstation item) {
       // 检查工位是否存在
   List <Workstation> existingWorkstation = workstationMapper.findById(item.getId());
    if (existingWorkstation == null) {
        return MyResult.error("工位不存在");
    }

    // 执行更新操作
    int result = workstationMapper.updateByadmin(id ,item);
    if (result > 0) {
        return MyResult.success("工位更新成功");
    } else {
        return MyResult.error("工位更新失败");
    }
    }

    @Override
    public MyResult getWorkstationByName(String name) {
        return MyResult.success(workstationMapper.findByName(name));
    }


}
