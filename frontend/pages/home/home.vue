<template>
  <view class="dashboard">
    <!-- 虚拟币余额展示 -->
    <u-card class="coin-balance">
      <u-cell title="虚拟币余额" :value="userCoinBalance" />
    </u-card>
    
    <!-- 设备使用统计图表 -->
    <u-card class="usage-chart">
      <u-chart type="pie" :chartData="equipmentUsageData" />
    </u-card>
    
    <!-- 近期租用记录 -->
    <u-card class="rental-history">
      <u-cell-group>
        <u-cell v-for="(record, index) in recentRentals" :key="index" :title="record.deviceName" :value="record.rentalTime + ' 天'" />
      </u-cell-group>
    </u-card>
  </view>
</template>

<script>
export default {
  data() {
    return {
      userCoinBalance: 1200,
      recentRentals: [
        { deviceName: '3D打印机', rentalTime: 5 },
        { deviceName: '激光切割机', rentalTime: 3 },
        // 添加更多记录
      ],
      equipmentUsageData: {
        categories: ['3D打印机', '激光切割机', '单反相机'],
        series: [{ name: '设备使用', data: [40, 30, 20] }],
      },
    };
  },
};
</script>

<style scoped>
.dashboard {
  padding: 20px;
}
.coin-balance {
  margin-bottom: 20px;
}
.usage-chart {
  margin-bottom: 20px;
}
.rental-history {
  margin-bottom: 20px;
}
</style>