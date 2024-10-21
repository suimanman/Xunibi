package com.example.xunibibackend.listener;

import com.example.xunibibackend.entity.*;
import com.example.xunibibackend.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class KeyExpirationListener implements MessageListener {

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private EquipmentMapper equipmentMapper;
    @Autowired
    private WorkstationMapper workstationMapper;
    @Autowired
    private CameraMapper cameraMapper;
    @Autowired
    private AreaMapper areaMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();

        // 检查 key 是否为租赁相关的 key
        if (expiredKey.startsWith("rental:")) {
            String[] parts = expiredKey.split(",");
            int type1=Integer.parseInt(parts[1]);
            String type2=parts[2];
            int teamId = Integer.parseInt(parts[3]);

            // 执行罚款逻辑
            handlePenalty(type1,type2, teamId);
        }
    }

    private void handlePenalty(Integer type1,String type2, int teamId) {
        if(type1 == 1){
            Workstation workstation = workstationMapper.selectByType(type2);
            Team team = teamMapper.selectByTeamId(teamId);

            // 计算双倍虚拟币惩罚
            Double penaltyCoins = workstation.getCoinConsumption() * 2;
            team.setVirtualCoins(team.getVirtualCoins() - penaltyCoins);
            teamMapper.updateCoinById(teamId, team.getVirtualCoins());

            System.out.println("Penalty applied: " + penaltyCoins + " coins for team: " + teamId);
        }else if(type1 == 2){
            Equipment equipment = equipmentMapper.selectByType(type2);
            Team team = teamMapper.selectByTeamId(teamId);

            // 计算双倍虚拟币惩罚
            Double penaltyCoins = equipment.getCoinConsumption() * 2;
            team.setVirtualCoins(team.getVirtualCoins() - penaltyCoins);
            teamMapper.updateCoinById(teamId, team.getVirtualCoins());

            System.out.println("Penalty applied: " + penaltyCoins + " coins for team: " + teamId);
        }else if(type1 == 3){
            Camera camera = cameraMapper.selectByType(type2);
            Team team = teamMapper.selectByTeamId(teamId);

            // 计算双倍虚拟币惩罚
            Double penaltyCoins = camera.getCoinConsumption() * 2;
            team.setVirtualCoins(team.getVirtualCoins() - penaltyCoins);
            teamMapper.updateCoinById(teamId, team.getVirtualCoins());

            System.out.println("Penalty applied: " + penaltyCoins + " coins for team: " + teamId);
        }else if(type1 == 4){
            Area area = areaMapper.selectByType(type2);
            Team team = teamMapper.selectByTeamId(teamId);

            // 计算双倍虚拟币惩罚
            Double penaltyCoins = area.getCoinConsumption() * 2;
            team.setVirtualCoins(team.getVirtualCoins() - penaltyCoins);
            teamMapper.updateCoinById(teamId, team.getVirtualCoins());

            System.out.println("Penalty applied: " + penaltyCoins + " coins for team: " + teamId);
        }

    }
}
