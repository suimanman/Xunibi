package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.SignInRecords;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;

@Mapper
public interface SignRecordMapper extends BaseMapper<SignInRecords> {
    @Insert("insert into SignInRecords (date,count) values (#{date},0)")
    void insertDate(LocalDate date);

    @Select("select count from SignInRecords where date=#{date}")
    Integer getCount(LocalDate date);

    @Update("update SignInRecords set count = #{count} where date=#{date}")
    void updateCount(LocalDate date,Integer count);
}
