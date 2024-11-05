package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.VirtualCoinTransaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RecordMapper extends BaseMapper<VirtualCoinTransaction> {
    @Select("select * from VirtualCoinTransactions where team_id = #{teamId}")
    List<VirtualCoinTransaction> getRecords(Integer teamId);
}
