package com.example.xunibibackend.service.impl;

import com.example.xunibibackend.entity.dto.TransactionSummaryDTO;
import com.example.xunibibackend.mapper.CoinTransactionMapper;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.VirtualCoinTransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VirtualCoinTransactionsServiceImpl implements VirtualCoinTransactionsService {
    @Autowired
    CoinTransactionMapper coinTransactionMapper;

    @Override
    public List<TransactionSummaryDTO> getMonthlyExpenditureSummary() {
         return coinTransactionMapper.getMonthlyExpenditureSummary();
    }

    @Override
    public List<TransactionSummaryDTO> getMonthlyEarnSummary() {
        return coinTransactionMapper.getMonthlyEarnSummary();
    }
}
