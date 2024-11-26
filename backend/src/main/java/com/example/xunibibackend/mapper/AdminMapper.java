package com.example.xunibibackend.mapper;

import com.example.xunibibackend.entity.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {
    @Select("SELECT * FROM Admin WHERE username = #{username}")
    Admin findByUsername(@Param("username") String username);

    @Select("SELECT * FROM Admin WHERE admin_id = #{adminId}")
    Admin findById(@Param("adminId") int adminId);

    @Insert("INSERT INTO Admin (username, password) VALUES (#{username}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "adminId")
    int createAdmin(Admin admin);

    @Update("UPDATE Admin SET username = #{username}, password = #{password} WHERE admin_id = #{adminId}")
    int updateAdmin(Admin admin);

    @Delete("DELETE FROM Admin WHERE admin_id = #{adminId}")
    int deleteAdmin(@Param("adminId") int adminId);

    @Select("SELECT * FROM Admin")
    List<Admin> findAllAdmins();
}

