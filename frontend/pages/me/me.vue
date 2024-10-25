<template>
	<view v-if="!isLogin" class="myself">
		<!-- <u--form labelPosition="left" :model="model1" :rules="rules" ref="uForm">
			<u-form-item label="姓名" prop="userInfo.name" borderBottom ref="item1">
				<u--input v-model="model1.userInfo.name" border="none">

				</u--input>
			</u-form-item>
		</u--form> -->
		<u-button @click="login" class="custom-style" type="success" shape="circle" size="large">点击登录</u-button>
	</view>
	<view v-else class="profile-page">
		<!-- Centered Avatar -->
		<view class="avatar-section">
			<u-avatar class="avatar" size="100" src="/static/avatar.png"></u-avatar>
			<text class="edit-avatar">编辑头像</text>
		</view>

		<u-cell-group class="cell-group">
			<u-cell title="用户名" value="河工大不知名学生" is-link @click="goEditUsername" />
			<u-cell title="修改密码" is-link @click="goEditUsername" />
			<u-cell title="人脸照片上传" @click="uploadPicture" />
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
		data() {
			return {
				isLogin: false,
				// rules: {
				// 	'userInfo.name': {
				// 		type: 'string',
				// 		required: true,
				// 		message: '请填写姓名',
				// 		trigger: ['blur', 'change']
				// 	},
				// }
			}
		},
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
		},
		// onReady() {
		//     	this.$refs.uForm.setRules(this.rules)
		//     },
	};
</script>

<style scoped>
	.myself {
		padding: 50px;
		display: flex;
		justify-content: center;
		/* 水平居中 */
		margin-top: 200px;
	}

	.cell-group {
		background-color: white;
		border-radius: 15px;
		border: 1px solid #e5e5e5;
		/* 边框颜色 */
		padding: 10px;
		/* 内边距 */
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

	.logout-section {
		margin: 30px 100px;
		display: flex;
		justify-content: center;
	}

	.custom-style {
		width: 100px;
		color: #f3f3f3;
		border-radius: 30px;
		border: none;
	}
</style>