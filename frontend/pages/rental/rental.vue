<template>
  <view class="device-page">
    <!-- 标题栏 -->
    <u-navbar title="设备列表" show-back-button />
    
    <!-- 设备卡片列表 -->
    <u-card v-for="(device, index) in devices" :key="index" class="device-card">
      <u-row>
        <u-col :span="6">
          <!-- 设备图片展示 -->
          <u-avatar shape="square" src="设备图片路径" size="lg" />
        </u-col>
        <u-col :span="18">
          <!-- 设备信息 -->
          <view class="device-info">
            <u-cell title="设备名称" :value="device.name" />
            <u-cell title="当前状态" :value="device.status" />
            <u-cell title="虚拟币消耗" :value="device.coinConsumption + '虚拟币/天'" />
            <u-cell title="是否空闲" :value="device.isFree ? '空闲' : '已租用'" />
          </view>
        </u-col>
      </u-row>
      <!-- 操作按钮 -->
      <u-button class="rent-btn" @click="rentDevice(device)">租用设备</u-button>
    </u-card>
  </view>
</template>

<script>
export default {
  data() {
    return {
      devices: [
        { name: '设备1', status: '可用', coinConsumption: 100, isFree: true },
        { name: '设备2', status: '租用中', coinConsumption: 200, isFree: false },
        // 添加其他设备数据
      ],
    };
  },
  methods: {
    rentDevice(device) {
      if (device.isFree) {
        // 执行租用逻辑
        this.$u.toast('设备租用成功！');
      } else {
        this.$u.toast('设备已被租用！');
      }
    },
  },
};
</script>

<style scoped>
.device-page {
  padding: 10px;
  background-color: #f5f5f5;
}
.device-card {
  margin-bottom: 20px;
}
.device-info {
  margin-left: 10px;
}
.rent-btn {
  margin-top: 10px;
  width: 100%;
  background-color: #42b983;
  color: white;
}
</style>