package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.VirtualCoinTransaction;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CoinTransactionMapper extends BaseMapper<VirtualCoinTransaction> {
}
