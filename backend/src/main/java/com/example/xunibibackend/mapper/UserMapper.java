package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Delete("delete from User where user_id=#{userId}")
    void deleteUserById(@Param("userId") Integer userId);
    @Select("select * from User")
    List<User> getAllUser();

    @Select("SELECT * from User where team_id = #{teamId}")
    List<User> getUsersByTeamId(@Param("teamId") Integer teamId);


    @Select("select * from User where username=#{username}")
    User getByUsername(String username);


    @Select("select * from user where user_id=#{id}")
    User getByUserId(@Param("id") Integer id);

    @Insert("insert into user(username,password,registration_date) " +
            "values (#{user.username},#{user.password},#{user.date})")
    Integer addUser(@Param("user") User user);

    @Update("update user set password = #{user.password} where user_id=#{user.userId}")
    User update(@Param("user") User user);

    @Update("update user set password = #{password} where user_id=#{userId}")
    void editPassword(Integer userId, String password);

    @Select("select team_id from user where username=#{username}")
    Integer getTeamIdByUsername(String username);

    @Insert("insert into User(username, password, role, team_id,registration_date)" +
            "values(#{user.username},#{user.password},#{user.role},#{user.teamId},#{user.date})")
    Integer createUser(@Param("user") User user);

   @Update("UPDATE User\n" +
           "SET \n" +
           "    username = #{user.username},\n" +
           "    password = #{user.password},\n" +
           "    role = #{user.role},\n" +
           "    team_id = #{user.teamId}\n" +
           "WHERE user_id = #{user.userId};")
    Integer updateById(@Param("id") Integer Id,  @Param("user")User user);



    @Select("select user_id from user where username = #{username}")
    Integer getUserIdByUsername(String userName);
}
