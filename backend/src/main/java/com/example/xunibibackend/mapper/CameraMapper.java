package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.Camera;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface CameraMapper extends BaseMapper<Camera> {
    @Select("select * from camera where type = #{type2}")
    Camera selectByType(String type2);
    @Update("update camera set is_available = #{i} ,rented_team_id = #{teamId}, " +
            "rental_date = #{rentDate},return_date = #{returnDate} where type = #{type}")
    void updateRental(Integer i, Integer teamId, String type, LocalDate rentDate, LocalDate returnDate);

    @Select("select * from camera")
    List<Camera> selectList();

    @Select("select * from camera where rented_team_id = #{teamId}")
    List<Camera> selectTeamRent(Integer teamId);
}
