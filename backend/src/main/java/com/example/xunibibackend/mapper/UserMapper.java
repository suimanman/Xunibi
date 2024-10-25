package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.Team;
import com.example.xunibibackend.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where user_id=#{id}")
    User getByUserId(Integer id);

    @Insert("insert into user values (#{username},#{password})")
    User addUser(String username,String password);

    @Update("update user set password = #{password} where user_id=#{userId}")
    User updateUser(String password,Integer userId);
}
