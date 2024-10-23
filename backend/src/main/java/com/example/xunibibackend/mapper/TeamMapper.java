package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.Team;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TeamMapper extends BaseMapper<Team> {
    @Select("select count(*) from team where team_name = #{name}")
    Integer selectName(String name);

    @Select("select * from team where team_id=#{id}")
    Team selectByTeamId(Integer id);

    @Update("update team set team_name=#{team.teamName} where team_id = #{id}")
    Integer updateTeam(Integer id,Team team);

    @Delete("delete from team where team_id=#{id}")
    void deleteTeamById(Integer id);

    @Update("update team set virtual_coins=#{coinNew} where team_id=#{teamId}")
    void updateCoinById(Integer teamId, Double coinNew);
}
