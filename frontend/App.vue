<template>
  <u-tabbar :active.sync="active">
    <u-tabbar-item icon="home" title="首页" @click="switchPage('/pages/home/home')"></u-tabbar-item>
    <u-tabbar-item icon="list" title="设备" @click="switchPage('/pages/devices/devices')"></u-tabbar-item>
    <u-tabbar-item icon="person" title="我的" @click="switchPage('/pages/profile/profile')"></u-tabbar-item>
  </u-tabbar>
</template>

<script>
export default {
  data() {
    return {
      active: 0
    };
  },
  methods: {
    switchPage(pagePath) {
      uni.switchTab({
        url: pagePath
      });
    }
  }
};
</script>

<style lang="scss">
	/* 注意要写在第一行，同时给style标签加入lang="scss"属性 */
	@import "@/uni_modules/uview-ui/index.scss";
</style>

