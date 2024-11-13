package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.Area;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface AreaMapper extends BaseMapper<Area> {
    @Select("select * from area where type = #{type2}")
    Area selectByType(String type2);
    @Update("update area set is_available = #{i} ,rented_team_id = #{teamId}, " +
            "rental_date = #{rentDate},return_date = #{returnDate} where type = #{type}")
    void updateRental(Integer i, Integer teamId, String type, LocalDate rentDate, LocalDate returnDate);

    @Select("select * from area")
    List<Area> selectList();

    @Select("select * from area where rented_team_id = #{teamId}")
    List<Area> selectTeamRent(Integer teamId);
}
