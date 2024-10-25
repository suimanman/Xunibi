package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where username=#{username}")
    User getByUsername(String username);

    @Select("select * from user where user_id=#{id}")
    User getByUserId(Integer id);

    @Insert("insert into user(username,password,role,registration_date) " +
            "values (#{user.username},#{user.password},#{user.role},#{user.date})")
    Integer addUser(@Param("user") User user);

    @Update("update user set password = #{user.password} where user_id=#{user.userId}")
    User update(@Param("user") User user);
}
