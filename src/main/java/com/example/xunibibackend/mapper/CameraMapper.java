package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.Camera;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CameraMapper extends BaseMapper<Camera> {
    @Select("select from camera where type = #{type2}")
    Camera selectByType(String type2);
    @Update("update camera set is_available = #{i} ,rented_team_id = #{teamId} where type = #{type}")
    void updateRental(int i, int teamId, String type2);
}
