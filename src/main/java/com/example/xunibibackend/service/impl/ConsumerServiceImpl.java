package com.example.xunibibackend.service.impl;

import com.example.xunibibackend.entity.*;
import com.example.xunibibackend.entity.dto.RentalRequest;
import com.example.xunibibackend.entity.dto.ReturnRequest;
import com.example.xunibibackend.mapper.*;
import com.example.xunibibackend.service.ConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;
@Slf4j
@Service
public class ConsumerServiceImpl implements ConsumerService {
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private RentalRecordMapper rentalRecordMapper;

    @Autowired
    private WorkstationMapper workstationMapper;
    @Autowired
    private EquipmentMapper equipmentMapper;
    @Autowired
    private AreaMapper areaMapper;
    @Autowired
    CameraMapper cameraMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 处理设备租赁逻辑
     */
    @Override
    public boolean rentEquipment(RentalRequest rentalRequest) {
        int rentalDays = rentalRequest.getRentalDays();
        Integer type1 = rentalRequest.getType1();
        String type2 = rentalRequest.getType2();
        int teamId = rentalRequest.getTeamId();

        Workstation workstation;
        Equipment equipment;
        Camera camera;
        Area area;

        Double coinConsumptionPerDay;
        Double totalCoinConsumption=0.0;

        // 获取团队信息
        Team team = teamMapper.selectByTeamId(teamId);

        if(type1 == 1){
            // 获取工位信息
            workstation = workstationMapper.selectByType(type2);
            if (workstation == null || workstation.getIsAvailable()==0) {
                return false; // 设备不可用
            }
            // 计算虚拟币消耗
            coinConsumptionPerDay = workstation.getCoinConsumption();
            totalCoinConsumption = rentalDays * coinConsumptionPerDay;
            // 检查团队的虚拟币余额是否足够
            if (team.getVirtualCoins() < totalCoinConsumption) {
                return false; // 虚拟币不足
            }
            workstationMapper.updateRental(0,teamId,type2);
        }else if(type1 == 2){
            // 获取打印机等设备信息
            equipment = equipmentMapper.selectByType(type2);
            if (equipment == null || equipment.getIsAvailable()==0) {
                return false; // 设备不可用
            }
            // 计算虚拟币消耗
            coinConsumptionPerDay = equipment.getCoinConsumption();
            totalCoinConsumption = rentalDays * coinConsumptionPerDay;
            if (team.getVirtualCoins() < totalCoinConsumption) {
                return false; // 虚拟币不足
            }
            equipmentMapper.updateRental(0,teamId,type2);
        }else if(type1 == 3){
            // 获取摄像设备信息
            camera = cameraMapper.selectByType(type2);
            if (camera == null || camera.getIsAvailable()==0) {
                return false; // 设备不可用
            }
            // 计算虚拟币消耗
            coinConsumptionPerDay = camera.getCoinConsumption();
            totalCoinConsumption = rentalDays * coinConsumptionPerDay;
            if (team.getVirtualCoins() < totalCoinConsumption) {
                return false; // 虚拟币不足
            }
            cameraMapper.updateRental(0,teamId,type2);
        }else if(type1 == 4){
            // 获取场地信息
            area = areaMapper.selectByType(type2);
            if (area == null || area.getIsAvailable()==0) {
                return false; // 设备不可用
            }

            // 计算虚拟币消耗
            coinConsumptionPerDay = area.getCoinConsumption();
            totalCoinConsumption = rentalDays * coinConsumptionPerDay;
            if (team.getVirtualCoins() < totalCoinConsumption) {
                return false; // 虚拟币不足
            }
            areaMapper.updateRental(0,teamId,type2);
        }

        // 扣除虚拟币
        team.setVirtualCoins(team.getVirtualCoins() - totalCoinConsumption);
        teamMapper.updateCoinById(teamId, team.getVirtualCoins());

        //将租用记录添加到租用记录表中
        RentalRecord rentalRecord=new RentalRecord();
        rentalRecord.setRentalDate(LocalDate.now());
        rentalRecord.setRentalDays(rentalRequest.getRentalDays());
        rentalRecord.setRentalType(type2);
        rentalRecord.setTeamId(teamId);
        rentalRecord.setCoinSpent(totalCoinConsumption);
        rentalRecord.setRentalOrPenalty(true);
        rentalRecordMapper.insert(rentalRecord);

        // 使用 Redis 设置租用时间计时器
        String redisKey = "rental:" + type1 +","+type2+ "," + teamId;
        long expireTime = (long)rentalDays * 24 * 60 * 60; // 秒数
        redisTemplate.opsForValue().set(redisKey, "rented", expireTime, TimeUnit.SECONDS);

        return true;
    }

    /**
     * 处理设备归还逻辑
     */
    @Override
    public boolean returnEquipment(ReturnRequest returnRequest) {
        String redisKey = "rental:" + returnRequest.getType1() + "," + returnRequest.getType2() + "," + returnRequest.getTeamId();
        int type1=returnRequest.getType1();
        String type2=returnRequest.getType2();
        // 检查 Redis 中是否存在租用记录
        if (redisTemplate.hasKey(redisKey)) {
            //更新借用设备或工位当前状态为可用
            if(type1 == 1){
                workstationMapper.updateRental(1,null,type2);
            }else if(type1 == 2){
                equipmentMapper.updateRental(1,null,type2);
            }else if(type1 == 3){
                cameraMapper.updateRental(1,null,type2);
            }else if(type1 == 4){
                areaMapper.updateRental(1,null,type2);
            }

            // 归还设备，删除 Redis 记录
            redisTemplate.delete(redisKey);
            return true; // 按时归还，无罚款
        } else {
            // 超时，罚款已触发
            return false; // 超时归还，罚款已扣除
        }
    }
}
