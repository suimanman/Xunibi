package com.example.xunibibackend.service;

import com.example.xunibibackend.entity.VirtualCoinTransaction;
import com.example.xunibibackend.entity.dto.TransactionSummaryDTO;
import com.example.xunibibackend.response.MyResult;

import java.util.List;

public interface VirtualCoinTransactionsService {
      List<TransactionSummaryDTO> getMonthlyExpenditureSummary();
       List<TransactionSummaryDTO> getMonthlyEarnSummary();
       MyResult getAllRecords();
}
