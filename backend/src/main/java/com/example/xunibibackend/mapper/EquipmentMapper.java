package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.Equipment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
}
