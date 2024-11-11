package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.Equipment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EquipmentMapper extends BaseMapper<Equipment> {
    @Select("select * from equipment where type = #{type2}")
    Equipment selectByType(String type2);

    @Update("update equipment set is_available = #{i} ,rented_team_id = #{teamId} where type = #{type}")
    void updateRental(Integer i, Integer teamId, String type2);

    @Select("select * from equipment")
    List<Equipment> selectList();
}
