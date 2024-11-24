package com.example.xunibibackend.service;

import com.example.xunibibackend.entity.Camera;
import com.example.xunibibackend.response.MyResult;
import org.springframework.stereotype.Service;

@Service
public interface CameraService {
 public MyResult getAllCameras() ;
    // 增加
    public MyResult addCamera(Camera item);
    // 删除
    public MyResult deleteCameraById(int id);
    // 修改
    public MyResult updateCamera(int id ,Camera item);

    // 按名称查找
   public MyResult getCameraByName(String name);
}
