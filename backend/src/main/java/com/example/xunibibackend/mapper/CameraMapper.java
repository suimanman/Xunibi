package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.Camera;
import com.example.xunibibackend.entity.Camera;
import org.apache.ibatis.annotations.*;

import java.util.List;

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

    //admin
@Select("""
    SELECT 
        id,
        type,
        coin_consumption,
        rented_team_id,  -- 如果租用，则返回队伍名称
        is_available,
        image_url
    FROM Camera""")
    List<Camera> getAllCameras();

    @Insert("INSERT INTO Camera (type, coin_consumption, is_available, rented_team_id, image_url) VALUES (#{item.type}, #{item.coinConsumption}, #{item.isAvailable},#{item.rentedTeamId}, #{item.imageUrl})")
    int insert(@Param("item") Camera item);

    @Delete("DELETE FROM Camera WHERE id = #{id}")
    int deleteById(int id);

    @Select("SELECT * FROM Camera WHERE id = #{id}")
    Camera findById(int id);

    @Select("SELECT * FROM Camera WHERE type = #{name}")
    List<Camera> findByName( String name);

   @Update("""
    UPDATE Camera
    SET 
        type = #{item.type}, 
        coin_consumption = #{item.coinConsumption}, 
        is_available = #{item.isAvailable},
        rented_team_id = #{item.rentedTeamId},
        image_url = #{item.imageUrl}
    WHERE id = #{id}
""")
    int updateByadmin(int id,@Param("item")Camera item);
}
