package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.Achievement;
import com.example.xunibibackend.entity.dto.AchievementByUserDTO;
import com.example.xunibibackend.response.MyResult;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AchievementMapper extends BaseMapper<Achievement> {

@Select("""
    SELECT a.*, t.team_name  AS teamName 
    FROM achievements a
    JOIN team t ON a.team_id = t.team_id
    WHERE a.status = '待审核'
""")
List<Achievement> getList();

@Select("""
    SELECT a.*
    FROM achievements a
  
    WHERE a.status = '待审核' and team_id = #{teamId}
""")
List<Achievement> getListById(@Param("teamId") int teamId);


@Select("""
SELECT * from achievements
""")
List<Achievement> getAllAchievement();

    @Update("update achievements set coin_awarded = #{achievement.coinAwarded},status = " +
            "#{achievement.status} where achievement_id = #{achievement.achievementId}")
    void updateCoinAndStatus(@Param("achievement") Achievement achievement);



    @Select("select  u.name as student_name, u.department, u.major, u.clazz, " +
            "t.team_name, a.achievement_name, a.achievement_type, a.achievement_date, a.coin_awarded, a.status " +
            "FROM Achievements a " +
            "LEFT JOIN  team t on a.team_id = t.team_id " +
            "LEFT JOIN User u ON u.user_id = a.user_id " +
            "ORDER BY u.user_id, a.achievement_date")
    List<AchievementByUserDTO> getAchievementByStu();


}
