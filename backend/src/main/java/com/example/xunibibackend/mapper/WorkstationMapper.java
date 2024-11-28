package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.Area;
import com.example.xunibibackend.entity.Workstation;
import com.example.xunibibackend.response.MyResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.*;

import java.util.List;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface WorkstationMapper extends BaseMapper<Workstation> {
    @Select("select * from workstation where type = #{type2}")
    Workstation selectByType(String type2);

    @Update("update workstation set is_available = #{i} ,rented_team_id = #{teamId}, " +
            "rental_date = #{rentDate},return_date = #{returnDate} where type = #{type}")
    void updateRental(Integer i, Integer teamId, String type, LocalDate rentDate,LocalDate returnDate);

    @Select("select * from workstation")
    List<Workstation> selectList();

    @Select("select * from workstation where rented_team_id = #{teamId}")
    List<Workstation> selectTeamRent(Integer teamId);
    
    

    // admin
   @Select("""
    SELECT 
        id,
        type,
        coin_consumption AS coinConsumption,
        is_available,
        rented_team_id  -- 如果租用，则返回队伍名称
    FROM 
        Workstation """) List<Workstation> getAllWorkstations();

    @Insert("INSERT INTO Workstation (type, coin_consumption, is_available , rented_team_id) VALUES (#{type}, #{coinConsumption}, #{isAvailable},#{rentedTeamId})")
    int insert(Workstation item);

    @Delete("DELETE FROM Workstation WHERE id = #{id}")
    int deleteById(int id);

    @Select("SELECT * FROM Workstation WHERE id = #{id}")
    List<Workstation> findById(int id);

    @Select("SELECT * FROM Workstation WHERE type = #{name}")
    List<Workstation> findByName(String name);

    @Update("""
    UPDATE Workstation
    SET 
        type = #{item.type}, 
        coin_consumption = #{item.coinConsumption}, 
        is_available = #{item.isAvailable},
        rented_team_id = #{item.rentedTeamId}
    WHERE id = #{id}
""")
    int updateByadmin(int id ,@Param("item")Workstation item);



}
