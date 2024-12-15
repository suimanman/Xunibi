package com.example.xunibibackend.service.impl;

import com.example.xunibibackend.entity.DutyRecord;
import com.example.xunibibackend.entity.Team;
import com.example.xunibibackend.mapper.DutyMapper;
import com.example.xunibibackend.mapper.TeamMapper;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.DutyRecordsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class DutyRecordsServiceImpl implements DutyRecordsService {

    @Autowired
    private DutyMapper dutyRecordsMapper;
    @Autowired
    private TeamMapper teamMapper;


    @Override
    public MyResult getAllDutyRecords() {
        List<DutyRecord> records = dutyRecordsMapper.getAllDutyRecord();
        if (records == null || records.isEmpty()) {
            return MyResult.error("没有找到");
        }
        return MyResult.success(records);
    }

    @Override
    public MyResult getDutyRecordById(int dutyId) {
        DutyRecord record = dutyRecordsMapper.findById(dutyId);
        if (record == null) {
            return MyResult.error("没有找到Id为" + dutyId +"这条记录");
        }
        return MyResult.success(record);
    }

    @Override
    public MyResult addDutyRecord(DutyRecord dutyRecord) {
        int result = dutyRecordsMapper.insert(dutyRecord);
        if (result > 0) {
            return MyResult.success("添加成功");
        } else {
            return MyResult.error("添加失败");
        }
    }

    @Override
    public MyResult updateDutyRecord(DutyRecord dutyRecord) {
        int result = dutyRecordsMapper.update(dutyRecord);
        if (result > 0) {
            return MyResult.success("更新成功");
        } else {
            return MyResult.error("更新失败");
        }
    }

    @Override
    public MyResult deleteDutyRecord(int dutyId) {
        int result = dutyRecordsMapper.deleteById(dutyId);
        if (result > 0) {
            return MyResult.success("删除成功");
        } else {
            return MyResult.error("删除失败");
        }
    }

    @Override
    public MyResult getReviewDutyRecord() {
        return MyResult.success(dutyRecordsMapper.getReviewDutyRecord());
    }

    @Override
public MyResult dutyReward(DutyRecord dutyRecord) {
    // 参数校验
    if (dutyRecord == null || dutyRecord.getTeamId() == null || dutyRecord.getCoinAwarded() == null) {
        return MyResult.error("参数不完整，无法处理奖励发放");
    }

    // 获取团队信息
    Team team = teamMapper.selectByTeamId(dutyRecord.getTeamId());
    if (team == null) {
        return MyResult.error("指定的团队不存在，无法发放奖励");
    }

    try {
        // 更新团队的虚拟币
        double updatedCoins = team.getVirtualCoins() + dutyRecord.getCoinAwarded();
        teamMapper.updateCoinById(team.getTeamId(), updatedCoins);

        // 更新值班记录
        int updateResult = dutyRecordsMapper.update(dutyRecord);
        if (updateResult == 0) {
            return MyResult.error("更新值班记录失败，请稍后重试");
        }

        // 返回成功结果
        return MyResult.success("奖励发放成功");

    } catch (Exception e) {
        log.error(e.getMessage());
        return MyResult.error("发放奖励时发生错误，请稍后重试");
    }
}

}
