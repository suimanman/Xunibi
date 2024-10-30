<template>
	<view class="me">
		<view class="contaier">
			<!-- 页面装饰图片 -->
			<image class="img-a" src="https://zhoukaiwen.com/img/loginImg/2.png"></image>
			<image class="img-b" src="https://zhoukaiwen.com/img/loginImg/3.png"></image>
			<!-- 标题 -->
			<view class="t-b">{{ title }}</view>
			<view class="t-b2">欢迎使用，虚拟币服务小程序</view>
			<form class="cl">
				<view class="t-a">
					<image src="@/static/icon/userId.png"></image>
					<view class="line"></view>
					<input placeholder="请输入学号" v-model="user.username" />
				</view>
				<view class="t-a">
					<image src="/static/icon/password.png"></image>
					<view class="line"></view>
					<input type="password" placeholder="请输入密码" v-model="user.password" />
				</view>
				<button @click="loginUser()">登 录</button>
				<button class="register" @click="registerUser()">注 册</button>
			</form>
			<!-- <view class="t-f"><text>————— 第三方账号登录 —————</text></view>
			 		<view class="t-e cl">
			 			<view class="t-g" @tap="wxLogin()"><image src="https://zhoukaiwen.com/img/loginImg/wx.png"></image></view>
			 			<view class="t-g" @tap="zfbLogin()"><image src="https://zhoukaiwen.com/img/loginImg/qq.png"></image></view>
			 		</view> -->
		</view>
	</view>
</template>

<script>
	import {
		setUserId
	} from "@/utils/auth";
	import {
		login,
		register,
		logout,
		isLogin
	} from "@/api/me"
	export default {
		data() {
			return {
				title: "众创空间",
				user: {
					username: '',
					password: '',
				},
			}
		},
		methods: {
			async registerUser() {
				try {
					const result = await register(this.user); // 调用 register 方法发送注册请求
					if (result.data.code === 200) {
						uni.showToast({
							title: "注册成功，正在登录...",
							icon: "success",
							duration: 2000
						});
						// 调用登录方法实现自动登录
						await this.loginUser();
					} else {
						uni.showToast({
							title: result.data.msg || "注册失败",
							icon: "none"
						});
					}
				} catch (error) {
					uni.showToast({
						title: "注册请求异常，请检查网络连接",
						icon: "none"
					});
				}
			},
			async loginUser() {
				try {
					const result = await login(this.user);
					if (result.data.code === 200) {
						console.log("here")
						// 获取并存储 Cookie
						const cookies = result.header['Set-Cookie'] || result.header['set-cookie'];
						if (cookies) {
							uni.setStorageSync('cookie', cookies);
						}
						uni.showToast({
							title: "登录成功",
							icon: "success",
							duration: 2000,
							success: () => {
								setTimeout(() => {
									uni.switchTab({
										url: '/pages/home/home' // tabBar 页面的路径
									});
								}, 1000);
							}
						});
						return {
							success: true,
							message: "登录成功"
						};
					} else if (result.data.code === 400) {
						uni.showToast({
							title: "用户名不存在",
							icon: "none"
						});
						return {
							success: false,
							message: "用户名不存在"
						};
					} else if (result.data.code === 406) {
						uni.showToast({
							title: "用户名或密码错误",
							icon: "none"
						});
						return {
							success: false,
							message: "用户名或密码错误"
						};
					} else {
						uni.showToast({
							title: "未知错误，请联系管理员",
							icon: "none"
						});
						return {
							success: false,
							message: "未知错误，请联系管理员"
						};
					}
				} catch (error) {
					uni.showToast({
						title: "登录请求异常，请检查网络连接",
						icon: "none"
					});
					return {
						success: false,
						message: "登录请求异常，请检查网络连接"
					};
				}
			},
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
</style>