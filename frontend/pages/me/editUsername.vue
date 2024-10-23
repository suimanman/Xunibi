<template>
  <view class="edit-page">
    <u-navbar title="用户名" left-arrow></u-navbar>
    <view class="input-section">
      <u-input v-model="username" placeholder="请输入用户名" />
    </view>
    <view class="btn-section">
      <u-button type="primary" @click="submit">确定</u-button>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      username: '河工大不知名学生'
    };
  },
  methods: {
    submit() {
      // Handle username submission
    }
  }
};
</script>

<style scoped>
.input-section {
  padding: 20px;
}
.btn-section {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>