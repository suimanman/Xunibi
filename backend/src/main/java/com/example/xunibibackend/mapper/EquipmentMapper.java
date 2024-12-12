package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.Equipment;
import com.example.xunibibackend.entity.Workstation;
import org.apache.ibatis.annotations.*;

import java.util.List;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EquipmentMapper extends BaseMapper<Equipment> {
    @Select("select * from equipment where type = #{type2}")
    Equipment selectByType(String type2);

    @Update("update equipment set is_available = #{i} ,rented_team_id = #{teamId}, " +
            "rental_date = #{rentDate},return_date = #{returnDate} where type = #{type}")
    void updateRental(Integer i, Integer teamId, String type, LocalDate rentDate,LocalDate returnDate);

    @Select("select * from equipment")
    List<Equipment> selectList();

    @Select("select * from equipment where rented_team_id = #{teamId}")
    List<Equipment> selectTeamRent(Integer teamId);


    //admin
   @Select("""
    SELECT 
        id,
        type,
        coin_consumption,
        rented_team_id AS rentedTeamId ,-- 如果租用，则返回队伍名称
        is_available AS isAvailable,
        image_url
    FROM 
        Equipment""")
   List<Equipment> getAllEquipments();

    @Insert("INSERT INTO Equipment (type, coin_consumption, is_available, rented_team_id, image_url) VALUES (#{item.type}, #{item.coinConsumption}, #{item.isAvailable}, #{item.rentedTeamId}, #{item.imageUrl})")
    int insert(@Param("item") Equipment item);

    @Delete("DELETE FROM Equipment WHERE id = #{id}")
    int deleteById(int id);

    @Select("SELECT * FROM Equipment WHERE id = #{id}")
    Equipment findById(int id);

    @Select("SELECT * FROM Equipment WHERE type = #{name}")
    List<Equipment> findByName(String name);


   @Update("""
    UPDATE Equipment
    SET 
        type = #{item.type}, 
        coin_consumption = #{item.coinConsumption}, 
        is_available = #{item.isAvailable},
        rented_team_id = #{item.rentedTeamId},
        image_url = #{item.imageUrl}
    WHERE id = #{id}
""")
    int updateByadmin(int id,@Param("item") Equipment item);

}
