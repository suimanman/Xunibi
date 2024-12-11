package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.DutyRecord;
import com.example.xunibibackend.entity.Equipment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DutyMapper extends BaseMapper<DutyRecord> {
    @Insert("INSERT INTO DutyRecords (team_id, user_id, duty_date, image, description,coin_awarded,status) " +
            "VALUES (#{dutyRecord.teamId}, #{dutyRecord.userId}, #{dutyRecord.dutyDate}, #{dutyRecord.image}, " +
            "#{dutyRecord.description},#{dutyRecord.coinAwarded},#{dutyRecord.status})")
    int insert(@Param("dutyRecord") DutyRecord dutyRecord);

}
