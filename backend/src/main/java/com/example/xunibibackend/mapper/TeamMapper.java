package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.Team;
import com.example.xunibibackend.entity.dto.TeamWithMembers;
import org.apache.ibatis.annotations.*;
import com.example.xunibibackend.entity.dto.TeamAchievementSummary;
import java.util.List;


@Mapper
public interface TeamMapper extends BaseMapper<Team> {
    @Select("select count(*) from team where team_name = #{name}")
    Integer selectName(String name);

    @Select("select * from team where team_id=#{id}")
    Team selectByTeamId(Integer id);

    @Update("update team set team_name=#{team.teamName}, virtual_coins = #{team.virtualCoins}, creation_date = #{team.creationDate} where team_id = #{id}")
    Integer updateTeam(Integer id,Team team);

    @Delete("delete from team where team_id=#{id}")
    void deleteTeamById(@Param("id") Integer id);

    @Update("update team set virtual_coins=#{coinNew} where team_id=#{teamId}")
    void updateCoinById(Integer teamId, Double coinNew);

    @Select("select team_id from team where team_name=#{name}")
    Integer getByTeamName(String name);

    @Select("select team_name from team where team_id=#{teamId}")
    String getNameByteamId(Integer teamId);

    @Select("select virtual_coins from team where team_id=#{team.teamId}")
    Double getCoin(@Param("team") Team team);

    @Insert("INSERT INTO Team (team_name, creation_date, virtual_coins) " +
            "VALUES (#{team.teamName}, #{team.creationDate}, #{team.virtualCoins})")
    int createTeamByAdmin(@Param("team")Team team);



    @Select("SELECT t.team_id, t.team_name, COUNT(u.user_id) AS team_member_count " +
            "FROM Team t " +
            "LEFT JOIN User u ON t.team_id = u.team_id " +
            "GROUP BY t.team_id, t.team_name")
    List<TeamWithMembers> getTeamsWithMemberCount();


     @Select("SELECT " +
            "t.team_id, " +
            "t.team_name, " +
            "SUM(CASE WHEN a.achievement_type = '论文' AND a.status = '已审核' THEN 1 ELSE 0 END) AS paper_count, " +
            "SUM(CASE WHEN a.achievement_type = '竞赛' AND a.status = '已审核' THEN 1 ELSE 0 END) AS competition_count, " +
            "SUM(CASE WHEN a.achievement_type NOT IN ('论文', '竞赛') AND a.status = '已审核' THEN 1 ELSE 0 END) AS other_count " +
            "FROM Team t " +
            "LEFT JOIN Achievements a ON t.team_id = a.team_id " +
            "WHERE a.status = '已审核' " +
            "GROUP BY t.team_id, t.team_name")
    List<TeamAchievementSummary> getTeamAchievementSummaries();




}
