package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.Team;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TeamMapper extends BaseMapper<Team> {
    @Select("select count(*) from team where team_name = #{name}")
    Integer selectName(String name);
}
