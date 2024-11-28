package com.example.xunibibackend.controller;

import com.example.xunibibackend.entity.Camera;
import com.example.xunibibackend.entity.Equipment;
import com.example.xunibibackend.entity.Workstation;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.CameraService;
import com.example.xunibibackend.service.EquipmentService;
import com.example.xunibibackend.service.WorkStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@CrossOrigin(
        origins = "http://localhost:8081", // 允许的前端地址
        allowCredentials = "true" // 允许携带凭证
)
public class ItemController {
    @Autowired
    private WorkStationService workStationService;
    @Autowired
    private EquipmentService equipmentService;

      @Autowired
    private CameraService cameraService;
    @GetMapping("/workstation/all")
    public MyResult getAllWorkstationItems() {
        return workStationService.getAllWorkstations();
    }

    @PostMapping("/workstation/add")
    public MyResult addWorkstation(@RequestBody Workstation item) {
        return workStationService.addWorkstation(item);
    }
    // 删除工位
    @DeleteMapping("/workstation/delete/{id}")
    public MyResult deleteWorkstation(@PathVariable int id) {
        return workStationService.deleteWorkstationById(id);
    }

    // 更新工位
    @PutMapping("/workstation/update/{id}")
    public MyResult updateWorkstation(@PathVariable int id, @RequestBody Workstation item) {
        // 可以在这里进一步实现具体的更新逻辑
        return workStationService.updateWorkstation(id, item);
    }

    @GetMapping("/workstation/findbyname")
    public MyResult getWorkstationByName(@RequestParam("name") String name) {
        return workStationService.getWorkstationByName(name);
    }

    // 设备

    @GetMapping("/equipment/all")
    public  MyResult getEquipmentItems() {
        return equipmentService.getAllEquipments();
    }
     @PostMapping("/equipment/add")
    public MyResult addEquipment(@RequestBody Equipment item) {
        return equipmentService.addEquipment(item);
     }

      // 删除工位
    @DeleteMapping("/equipment/delete/{id}")
    public MyResult deleteEquipment(@PathVariable int id) {
        return equipmentService.deleteEquipmentById(id);
    }

    @PutMapping("/equipment/update/{id}")
    public MyResult updateEquipment(@PathVariable int id,@RequestBody Equipment item) {
        return equipmentService.updateEquipment(id, item);
    }

     @GetMapping("/equipment/findbyname")
    public MyResult getEquipmentByName(@RequestParam("itemName") String itemName) {
        return equipmentService.getAEquipmentsByName(itemName);
    }


    // 相机

    @GetMapping("/camera/all")
    public  MyResult getCameraItems() {
        return cameraService.getAllCameras();
    }
     @PostMapping("/camera/add")
    public MyResult addCamera(@RequestBody Camera item) {
        return cameraService.addCamera(item);
     }

      // 删除
    @DeleteMapping("/camera/delete/{id}")
    public MyResult deleteCamera(@PathVariable int id) {
        return cameraService.deleteCameraById(id);
    }

    @PutMapping("/camera/update/{id}")
    public MyResult updateCamera(@PathVariable int id,@RequestBody Camera item) {
        return cameraService.updateCamera(id, item);
    }

    @GetMapping("/camera/findbyname")
    public MyResult getItemByName(@RequestParam("itemName") String itemName) {
        return equipmentService.getAEquipmentsByName(itemName);
    }
}
