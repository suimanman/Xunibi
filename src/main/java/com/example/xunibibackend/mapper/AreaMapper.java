package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.Area;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AreaMapper extends BaseMapper<Area> {
    @Select("select from area where type = #{type2}")
    Area selectByType(String type2);
    @Update("update area set is_available = #{i} ,rented_team_id = #{teamId} where type = #{type}")
    void updateRental(int i, int teamId, String type2);
}
