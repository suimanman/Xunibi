<template>
  <view class="profile-page">
    <!-- Centered Avatar -->
    <view class="avatar-section">
      <u-avatar class="avatar" size="100" src="/static/avatar.png"></u-avatar>
      <text class="edit-avatar">编辑头像</text>
    </view>

    <u-cell-group class="cell-group">
      <u-cell title="用户名" value="河工大不知名学生" is-link @click="goEditUsername" />
      <u-cell title="性别" value="男" is-link @click="goEditGender" />
      <u-cell title="所属团队" value="河工大" />
    </u-cell-group>

    <!-- Logout Button -->
    <view class="logout-section">
      <u-button class="custom-style" type="success" shape="circle" size="large" @click="logout">退出登录</u-button>
    </view>
  </view>
</template>

<script>
export default {
  methods: {
    goEditUsername() {
      uni.navigateTo({
        url: '/pages/me/editUsername'
      });
    },
    goEditGender() {
      uni.navigateTo({
        url: '/pages/me/editGender'
      });
    },
    logout() {
      // Handle logout
    }
  }
};
</script>

<style scoped>
	.cell-group{
		background-color: white;
		border-radius: 15px;
		border: 1px solid #e5e5e5; /* 边框颜色 */
		padding: 10px; /* 内边距 */
	}
	.avatar-section {
	  display: flex;
	  flex-direction: column;
	  align-items: center;
	  margin: 20px 0;
	}
	.edit-avatar {
	  margin-top: 10px;
	  font-size: 14px;
	  color: #666;
	}
	.logout-section{
	  margin: 30px 100px;
	  display: flex;
	  justify-content: center;
	}
	.custom-style{
		color: #f3f3f3;
		border-radius: 30px;
		border: none;
	}
	
</style>