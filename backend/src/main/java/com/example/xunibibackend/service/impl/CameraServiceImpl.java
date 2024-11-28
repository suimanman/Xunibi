package com.example.xunibibackend.service.impl;

import com.example.xunibibackend.entity.Camera;
import com.example.xunibibackend.mapper.CameraMapper;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.CameraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CameraServiceImpl implements CameraService {

    @Autowired
    CameraMapper CameraMapper;

    // 查询所有相机
    public MyResult getAllCameras() {
        List<Camera> Cameras = CameraMapper.getAllCameras();
        return MyResult.success(Cameras); // 返回查询结果
    }

    // 新增相机
    public MyResult addCamera(Camera item) {
        int result = CameraMapper.insert(item);
        if (result > 0) {
            return MyResult.success("相机新增成功");
        } else {
            return MyResult.error("相机新增失败");
        }
    }

    // 删除相机
    public MyResult deleteCameraById(int id) {
        int result = CameraMapper.deleteById(id);
        if (result > 0) {
            return MyResult.success("相机删除成功");
        } else {
            return MyResult.error("相机删除失败");
        }
    }

    @Override
    public MyResult updateCamera(int id,Camera item) {
       // 检查相机是否存在
    Camera existingCamera = CameraMapper.findById(item.getId());
    if (existingCamera == null) {
        return MyResult.error("相机不存在");
    }

    // 执行更新操作
    int result = CameraMapper.updateByadmin(id, item);
    if (result > 0) {
        return MyResult.success("相机更新成功");
    } else {
        return MyResult.error("相机更新失败");
    }
    }

    @Override
    public MyResult getCameraByName(String name) {
        return MyResult.success(CameraMapper.findByName(name));
    }


}
