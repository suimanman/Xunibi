package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.Achievement;
import com.example.xunibibackend.response.MyResult;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AchievementMapper extends BaseMapper<Achievement> {

    @Select("select * from achievements where status='待审核'")
    List<Achievement> getList();

    @Update("update achievements set coin_awarded = #{achievement.coinAwarded},status = " +
            "#{achievement.status} where achievement_id = #{achievement.achievementId}")
    void updateCoinAndStatus(@Param("achievement") Achievement achievement);
}
