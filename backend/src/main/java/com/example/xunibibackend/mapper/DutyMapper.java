package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.DutyRecord;
import com.example.xunibibackend.entity.Equipment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DutyMapper extends BaseMapper<DutyRecord> {
    @Insert("INSERT INTO DutyRecords (team_id, user_id, duty_date, image, description,coin_awarded,status) " +
            "VALUES (#{dutyRecord.teamId}, #{dutyRecord.userId}, #{dutyRecord.dutyDate}, #{dutyRecord.image}, " +
            "#{dutyRecord.description},#{dutyRecord.coinAwarded},#{dutyRecord.status})")
    int insert(@Param("dutyRecord") DutyRecord dutyRecord);

    // admin
    @Select("""
    select * from DutyRecords
    """)
    List<DutyRecord> getAllDutyRecord();

    @Select("""
    select * from DutyRecords where status ='待审核'
    """)
    List<DutyRecord> getReviewDutyRecord();


    @Select("SELECT * FROM DutyRecords WHERE duty_id = #{dutyId}")
    DutyRecord findById(@Param("dutyId") int dutyId);


    @Update("UPDATE DutyRecords SET team_id = #{teamId}, user_id = #{userId}, duty_date = #{dutyDate}, " +
            "coin_awarded = #{coinAwarded}, image = #{image}, status = #{status}, description = #{description} " +
            "WHERE duty_id = #{dutyId}")
    int update(DutyRecord dutyRecords);

    @Delete("DELETE FROM DutyRecords WHERE duty_id = #{dutyId}")
    int deleteById(@Param("dutyId") int dutyId);










}
