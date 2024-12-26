package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.RentalRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RentalRecordMapper extends BaseMapper<RentalRecord> {

    @Select("""
    select  * from  RentalRecords
        """)
    List<RentalRecord> getAllRentalRecord();


}
