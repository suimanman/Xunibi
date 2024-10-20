package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.TrainRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TrainMapper extends BaseMapper<TrainRecord> {
}
