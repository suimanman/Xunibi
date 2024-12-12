package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.Notice;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
    @Select("select content from notice where display=1")
    List <String> getNotice();



    // admin
    @Select("SELECT * FROM notice")
    List<Notice> findAll();

    @Select("SELECT * FROM notice WHERE id = #{id}")
    Notice findById(@Param("id") int id);

    @Insert("INSERT INTO notice (content, created_at, display) VALUES (#{content}, #{createdAt}, #{display})")
    int insert(Notice notice);

    @Update("UPDATE notice SET content = #{content}, display = #{display} WHERE id = #{id}")
    void update(Notice notice);

    @Delete("DELETE FROM notice WHERE id = #{id}")
    void delete(@Param("id") int id);




}
