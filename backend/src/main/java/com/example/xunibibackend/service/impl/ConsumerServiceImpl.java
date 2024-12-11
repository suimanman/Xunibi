package com.example.xunibibackend.service.impl;

import com.example.xunibibackend.entity.*;
import com.example.xunibibackend.entity.dto.RentalRequest;
import com.example.xunibibackend.entity.dto.ReturnRequest;
import com.example.xunibibackend.entity.dto.TeamRent;
import com.example.xunibibackend.mapper.*;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.ConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
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
    CoinTransactionMapper coinTransactionMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 处理设备租赁逻辑
     */
    @Override
    public boolean rentEquipment(RentalRequest rentalRequest) {
        //虚拟币交易记录
        VirtualCoinTransaction coinTransaction=new VirtualCoinTransaction();
        coinTransaction.setTransactionDate(LocalDate.now());
        coinTransaction.setTransactionType("支出");

        int rentalDays = rentalRequest.getRentalDays();
        String typeString = rentalRequest.getType1();
        int type1=0;
        switch (typeString) {
            case "工位" -> type1 = 1;
            case "固定设备" -> type1 = 2;
            case "摄像设备" -> type1 = 3;
            case "场地" -> type1 = 4;
        }
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
        //虚拟币交易记录
        coinTransaction.setTeamId(teamId);
        if(type1 == 1){
            // 获取工位信息
            workstation = workstationMapper.selectByType(type2);
            if (workstation == null || workstation.getIsAvailable()==0) {
                return false; // 设备不可用
            }
            // 计算虚拟币消耗
            coinConsumptionPerDay = workstation.getCoinConsumption();
            totalCoinConsumption = rentalDays * coinConsumptionPerDay;

            //虚拟币交易记录消耗
            coinTransaction.setCoinAmount(totalCoinConsumption);
            coinTransaction.setDescription("租用工位");
            // 检查团队的虚拟币余额是否足够
            if (team.getVirtualCoins() < totalCoinConsumption) {
                return false; // 虚拟币不足
            }
            //可以租用，则更新租用时间和归还时间
            LocalDate rentDate = LocalDate.now();
            LocalDate returnDate = rentDate.plusDays(rentalDays);

            workstationMapper.updateRental(0,teamId,type2,rentDate,returnDate);
        }else if(type1 == 2){
            // 获取打印机等设备信息
            equipment = equipmentMapper.selectByType(type2);
            if (equipment == null || equipment.getIsAvailable()==0) {
                return false; // 设备不可用
            }
            // 计算虚拟币消耗
            coinConsumptionPerDay = equipment.getCoinConsumption();
            totalCoinConsumption = rentalDays * coinConsumptionPerDay;

            //虚拟币交易记录消耗
            coinTransaction.setCoinAmount(totalCoinConsumption);
            coinTransaction.setDescription("租用"+type2);

            if (team.getVirtualCoins() < totalCoinConsumption) {
                return false; // 虚拟币不足
            }
            //可以租用，则更新租用时间和归还时间
            LocalDate rentDate = LocalDate.now();
            LocalDate returnDate = rentDate.plusDays(rentalDays);
            equipmentMapper.updateRental(0,teamId,type2,rentDate,returnDate);
        }else if(type1 == 3){
            // 获取摄像设备信息
            camera = cameraMapper.selectByType(type2);
            if (camera == null || camera.getIsAvailable()==0) {
                return false; // 设备不可用
            }
            // 计算虚拟币消耗
            coinConsumptionPerDay = camera.getCoinConsumption();
            totalCoinConsumption = rentalDays * coinConsumptionPerDay;

            //虚拟币交易记录消耗
            coinTransaction.setCoinAmount(totalCoinConsumption);
            coinTransaction.setDescription("租用"+type2);

            if (team.getVirtualCoins() < totalCoinConsumption) {
                return false; // 虚拟币不足
            }
            //可以租用，则更新租用时间和归还时间
            LocalDate rentDate = LocalDate.now();
            LocalDate returnDate = rentDate.plusDays(rentalDays);
            cameraMapper.updateRental(0,teamId,type2,rentDate,returnDate);
        }else if(type1 == 4){
            // 获取场地信息
            area = areaMapper.selectByType(type2);
            if (area == null || area.getIsAvailable()==0) {
                return false; // 设备不可用
            }

            // 计算虚拟币消耗
            coinConsumptionPerDay = area.getCoinConsumption();
            totalCoinConsumption = rentalDays * coinConsumptionPerDay;

            //虚拟币交易记录消耗
            coinTransaction.setCoinAmount(totalCoinConsumption);
            coinTransaction.setDescription("租用"+type2);

            if (team.getVirtualCoins() < totalCoinConsumption) {
                return false; // 虚拟币不足
            }
            //可以租用，则更新租用时间和归还时间
            LocalDate rentDate = LocalDate.now();
            LocalDate returnDate = rentDate.plusDays(rentalDays);
            areaMapper.updateRental(0,teamId,type2,rentDate,returnDate);
        }

        // 扣除虚拟币
        team.setVirtualCoins(team.getVirtualCoins() - totalCoinConsumption);
        teamMapper.updateCoinById(teamId, team.getVirtualCoins());
//        log.info("----------------");
        //将租用记录添加到租用记录表中
        RentalRecord rentalRecord=new RentalRecord();
        rentalRecord.setRentalDate(LocalDate.now());
        rentalRecord.setRentalDays(rentalRequest.getRentalDays());
        rentalRecord.setRentalType(type2);
        rentalRecord.setTeamId(teamId);
        rentalRecord.setCoinSpent(totalCoinConsumption);
        rentalRecord.setRentalOrReturn(true);
        rentalRecordMapper.insert(rentalRecord);
//        log.info("----------------");
        //将虚拟币交易记录添加到表中
        coinTransactionMapper.insert(coinTransaction);

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
        log.info("returnRequest:{}",returnRequest);
        String typeString=returnRequest.getType1();
        int type1=0;
        switch (typeString) {
            case "工位" -> type1 = 1;
            case "固定设备" -> type1 = 2;
            case "摄像设备" -> type1 = 3;
            case "场地" -> type1 = 4;
            case "已租用"-> type1=5;
        }
        Workstation workstation=workstationMapper.selectByType(returnRequest.getType2());
        Equipment equipment=equipmentMapper.selectByType(returnRequest.getType2());
        Camera camera=cameraMapper.selectByType(returnRequest.getType2());
        Area area=areaMapper.selectByType(returnRequest.getType2());
        if(type1==5){
            if(workstation!=null) type1=1;
            else if(equipment!=null) type1=2;
            else if(camera!=null) type1=3;
            else if(area!=null) type1=4;
        }
        String redisKey = "rental:" + type1 + "," + returnRequest.getType2() + "," + returnRequest.getTeamId();
        String type2=returnRequest.getType2();
        // 检查 Redis 中是否存在租用记录
        if (Boolean.TRUE.equals(redisTemplate.hasKey(redisKey))) {
            log.info("查询成功，可以归还");
            //更新借用设备或工位当前状态为可用
            if(type1 == 1){
                workstationMapper.updateRental(1,null,type2,null,null);
            }else if(type1 == 2){
                equipmentMapper.updateRental(1,null,type2,null,null);
            }else if(type1 == 3){
                cameraMapper.updateRental(1,null,type2,null,null);
            }else if(type1 == 4){
                areaMapper.updateRental(1,null,type2,null,null);
            }

            // 归还设备，删除 Redis 记录
            redisTemplate.delete(redisKey);
            return true; // 按时归还，无罚款
        } else {
            // 超时，罚款触发
            //虚拟币交易记录
            VirtualCoinTransaction coinTransaction=new VirtualCoinTransaction();
            coinTransaction.setTransactionDate(LocalDate.now());
            coinTransaction.setTransactionType("支出");
            // 获取团队信息
            Team team = teamMapper.selectByTeamId(returnRequest.getTeamId());
            LocalDate returnDate=LocalDate.now();
            double coins=0.0;
            long days=0;
            if(type1==1){
                days= ChronoUnit.DAYS.between(workstation.getReturnDate(),returnDate);
                coins=days*workstation.getCoinConsumption()*2;
                workstationMapper.updateRental(1,null,type2,null,null);
            } else if (type1==2) {
                days= ChronoUnit.DAYS.between(returnDate,equipment.getReturnDate());
                coins=days*equipment.getCoinConsumption()*2;
                equipmentMapper.updateRental(1,null,type2,null,null);
            }else if (type1==3) {
                days= ChronoUnit.DAYS.between(returnDate,camera.getReturnDate());
                coins=days*camera.getCoinConsumption()*2;
                cameraMapper.updateRental(1,null,type2,null,null);
            }else if (type1==4) {
                days= ChronoUnit.DAYS.between(returnDate,area.getReturnDate());
                coins=days*area.getCoinConsumption()*2;
                areaMapper.updateRental(1,null,type2,null,null);
            }
            coinTransaction.setTeamId(returnRequest.getTeamId());
            coinTransaction.setDescription("租用"+returnRequest.getType2()+"超时"+days+"天");
            coinTransaction.setCoinAmount(coins);
            //更新虚拟币交易记录表
            coinTransactionMapper.insert(coinTransaction);
            // 扣除虚拟币
            team.setVirtualCoins(team.getVirtualCoins() - coins);
            teamMapper.updateCoinById(returnRequest.getTeamId(), team.getVirtualCoins());
            return false; // 超时归还，罚款已扣除
        }
    }

    @Override
    public MyResult getList(String type) {
        String teamName=null;
        switch (type) {
            case "工位" -> {
                List<Workstation> workstationList = workstationMapper.selectList();
                for(Workstation workstation: workstationList){
                    Integer teamId=workstation.getRentedTeamId();
                    if(teamId != null)
                        teamName=teamMapper.getNameByteamId(teamId);
                    workstation.setRentTeamName(teamName);
                }
                log.info("资源列表1：{}",workstationList);
                return MyResult.success(workstationList);
            }
            case "固定设备" -> {
                List<Equipment> equipmentList = equipmentMapper.selectList();
                for(Equipment equipment: equipmentList){
                    Integer teamId=equipment.getRentedTeamId();
                    if(teamId != null)
                        teamName=teamMapper.getNameByteamId(teamId);
                    equipment.setRentTeamName(teamName);
                }
                log.info("资源列表2：{}",equipmentList);
                return MyResult.success(equipmentList);
            }
            case "摄像设备" -> {
                List<Camera> cameraList = cameraMapper.selectList();
                for(Camera camera: cameraList){
                    Integer teamId=camera.getRentedTeamId();
                    if(teamId != null)
                        teamName=teamMapper.getNameByteamId(teamId);
                    camera.setRentTeamName(teamName);
                }
                log.info("资源列表3：{}",cameraList);
                return MyResult.success(cameraList);
            }
            case "场地" -> {
                List<Area> areaList = areaMapper.selectList();
                for(Area area: areaList){
                    Integer teamId=area.getRentedTeamId();
                    if(teamId != null)
                        teamName=teamMapper.getNameByteamId(teamId);
                    area.setRentTeamName(teamName);
                }
                log.info("资源列表4：{}",areaList);
                return MyResult.success(areaList);
            }
            default -> {
                return MyResult.error("无效的资源类型！");
            }
        }
    }

    /*
        获取登录用户所在团队已租用的资源
     */
    @Override
    public MyResult getRentList(Integer teamId) {
        //分别从4
        List<TeamRent> teamRents = new ArrayList<>();
        List<Area> areas = new ArrayList<>();
        List<Workstation> workstations = new ArrayList<>();
        List<Equipment> equipments = new ArrayList<>();
        List<Camera> cameras = new ArrayList<>();

        areas = areaMapper.selectTeamRent(teamId);
        workstations = workstationMapper.selectTeamRent(teamId);
        equipments = equipmentMapper.selectTeamRent(teamId);
        cameras = cameraMapper.selectTeamRent(teamId);

        if (areas != null && !areas.isEmpty()) {
            teamRents.addAll(
                    areas.stream()
                            .map(TeamRent::new)  // 假设有适合的构造函数
                            .toList()
            );
        }
        if (workstations != null && !workstations.isEmpty()) {
            teamRents.addAll(
                    workstations.stream()
                            .map(TeamRent::new)
                            .toList()
            );
        }
        if (equipments != null && !equipments.isEmpty()) {
            teamRents.addAll(
                    equipments.stream()
                            .map(TeamRent::new)
                            .toList()
            );
        }
        if (cameras != null && !cameras.isEmpty()) {
            teamRents.addAll(
                    cameras.stream()
                            .map(TeamRent::new)
                            .toList()
            );
        }
//        log.info("{}团队所租用的资源：{}",teamId,teamRents);
        if(teamRents.isEmpty()) return MyResult.error("暂无租用");
        return MyResult.success(teamRents);
    }
}
