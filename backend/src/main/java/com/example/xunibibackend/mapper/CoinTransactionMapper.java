package com.example.xunibibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.xunibibackend.entity.VirtualCoinTransaction;
import com.example.xunibibackend.entity.dto.TransactionSummaryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CoinTransactionMapper extends BaseMapper<VirtualCoinTransaction>{



@Select("SELECT DATE_FORMAT(transaction_date, '%Y-%m') AS month, " +
            "description, COUNT(*) AS transaction_count, SUM(coin_amount) AS total_expenditure " +
            "FROM VirtualCoinTransactions " +
            "WHERE transaction_type = '支出' " +
            "GROUP BY month, description " +
            "ORDER BY month DESC, description")
List<TransactionSummaryDTO> getMonthlyExpenditureSummary();


@Select("SELECT DATE_FORMAT(transaction_date, '%Y-%m') AS month, " +
            "description, COUNT(*) AS transaction_count, SUM(coin_amount) AS total_expenditure " +
            "FROM VirtualCoinTransactions " +
            "WHERE transaction_type = '收入' " +
            "GROUP BY month, description " +
            "ORDER BY month DESC, description")
List<TransactionSummaryDTO> getMonthlyEarnSummary();

    @Select("select * from VirtualCoinTransactions ")
    List<VirtualCoinTransaction> getAllRecords();


}
