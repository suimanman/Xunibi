package com.example.xunibibackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SignCoinMapper {
    @Select("select coin from SignCoin")
    Double findCoin();

    @Update("update SignCoin set coin = #{coin}")
    void setCoin(Double coin);
}
