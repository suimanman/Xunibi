<template>
	<view class="me">

		<image class="img-b" src="https://zhoukaiwen.com/img/loginImg/3.png"></image>

		<u-cell-group class="cell-group">
			<u-cell title="学号" v-model="user.username" />
			<u-cell title="所属团队" v-model="user.teamName" />
			<u-cell title="修改密码" is-link @click="goEditPassword" />
			<u-cell title="其他信息" is-link @click="editElseInfo" />
		</u-cell-group>
		<button class="logout-button" @click="logout()">退 出 登 录</button>
	</view>
</template>

<script>
	import {
		setUserId
	} from "@/utils/auth";
	import {
		login,
		register,
		isLogin,
		logout
	} from "@/api/me"
	export default {
		data() {
			return {
				title: "众创空间",
				user: {
					username: '',
					password: '',
					role: '',
					teamName: '',
				},
			}
		},
		created() {
			this.loginHandle();
			if (isLogin()) {
				// console.log("登录");
				this.getUserInfo();
			} else {
				console.log("未登录");
			}
		},
		//下拉刷新
		onPullDownRefresh() {
			// 处理刷新逻辑，比如重新请求数据
			this.getUserInfo();

			// 模拟数据请求完成，调用 stopPullDownRefresh 停止刷新动画
			setTimeout(() => {
				uni.stopPullDownRefresh();
			}, 1000); // 1秒后停止刷新动画，可以根据实际情况调整时间
		},
		methods: {
			async loginHandle() {
				// 调用 isLogin 并等待其返回结果
				const loginResult = await isLogin();
				if (!isLogin()) {
					uni.navigateTo({
						url: '/pages/login/login'
					})
				} else {
					// 获取用户数据
					this.user = loginResult.data.data; // data 中包含返回的用户数据
					console.log(this.user);
				}
			},
			async logout() {
				await logout();
				uni.removeStorage({
					key: 'userInfo',
					success: function() {
						console.log('User data removed successfully');
					},
					fail: function(error) {
						console.log('Failed to remove user data:', error);
					}
				});
				uni.showToast({
					title: "已退出登录",
					icon: "success",
					duration: 2000,
					success: () => {
						// 使用 reLaunch 进行跳转，确保退出后返回登录页面
						uni.reLaunch({
							url: '/pages/login/login'
						});
					}
				});
			},
			goEditPassword() {
				uni.redirectTo({
					url: '/pages/me/editPassword'
				});
			},
			editElseInfo(){
				uni.redirectTo({
					url: '/pages/me/editElseInfo'
				});
			}
		},
	};
</script>

<style lang="scss" scoped>
	.contaier {
		height: 100vh;
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

	.custom-style {
		width: 100px;
		color: #f3f3f3;
		border-radius: 30px;
		border: none;
	}

	.img-a {
		position: absolute;
		width: 100%;
		top: -150rpx;
		right: 0;
	}

	.img-b {
		position: absolute;
		width: 50%;
		bottom: 0;
		left: -50rpx;
		/* margin-bottom: -200rpx; */
	}

	.me {
		width: 650rpx;
		margin: 0 auto;
		font-size: 28rpx;
		color: #000;
	}

	.me button {
		font-size: 28rpx;
		background: #5677fc;
		color: #fff;
		height: 90rpx;
		line-height: 90rpx;
		border-radius: 50rpx;
		box-shadow: 0 5px 7px 0 rgba(86, 119, 252, 0.2);
	}

	.me input {
		padding: 0 20rpx 0 120rpx;
		height: 90rpx;
		line-height: 90rpx;
		margin-bottom: 50rpx;
		background: #f8f7fc;
		border: 1px solid #e9e9e9;
		font-size: 28rpx;
		border-radius: 50rpx;
	}

	.me .t-a {
		position: relative;
	}

	.me .t-a image {
		width: 40rpx;
		height: 40rpx;
		position: absolute;
		left: 40rpx;
		top: 28rpx;
		/* border-right: 2rpx solid #dedede; */
		margin-right: 20rpx;
	}

	.me .t-a .line {
		width: 2rpx;
		height: 40rpx;
		background-color: #dedede;
		position: absolute;
		top: 28rpx;
		left: 98rpx;
	}

	.me .t-b {
		text-align: left;
		font-size: 46rpx;
		color: #000;
		padding: 300rpx 0 30rpx 0;
		font-weight: bold;
	}

	.me .t-b2 {
		text-align: left;
		font-size: 32rpx;
		color: #aaaaaa;
		padding: 0rpx 0 120rpx 0;
	}

	.me .t-c {
		position: absolute;
		right: 22rpx;
		top: 22rpx;
		background: #5677fc;
		color: #fff;
		font-size: 24rpx;
		border-radius: 50rpx;
		height: 50rpx;
		line-height: 50rpx;
		padding: 0 25rpx;
	}

	.me .t-d {
		text-align: center;
		color: #999;
		margin: 80rpx 0;
	}

	.me .t-e {
		text-align: center;
		width: 250rpx;
		margin: 80rpx auto 0;
	}

	.me .t-g {
		float: left;
		width: 50%;
	}

	.me .t-e image {
		width: 50rpx;
		height: 50rpx;
	}

	.me .t-f {
		text-align: center;
		margin: 200rpx 0 0 0;
		color: #666;
	}

	.me .t-f text {
		margin-left: 20rpx;
		color: #aaaaaa;
		font-size: 27rpx;
	}

	.me .uni-input-placeholder {
		color: #000;
	}

	.cl {
		zoom: 1;
	}

	.cl:after {
		clear: both;
		display: block;
		visibility: hidden;
		height: 0;
		content: '\20';
	}

	.register {
		top: 10px;
	}

	.logout-button {
		margin-top: 30px;
		/* 调整按钮的顶部间距 */
		font-size: 28rpx;
		background: #5677fc;
		color: #fff;
		height: 90rpx;
		line-height: 90rpx;
		border-radius: 50rpx;
		box-shadow: 0 5px 7px 0 rgba(86, 119, 252, 0.2);
	}
</style>