<template>
	<view class="edit-password">
		<u-form ref="formRef" :model="form" @submit="submitForm">
			<u-form-item label="原密码" required>
				<u-input type="password" v-model="form.oldPassword" placeholder="请输入原密码" />
			</u-form-item>

			<u-form-item label="新密码" required>
				<u-input type="password" v-model="form.newPassword" placeholder="请输入新密码" />
			</u-form-item>

			<u-form-item label="确认新密码" required>
				<u-input type="password" v-model="form.confirmNewPassword" placeholder="请再次输入新密码" />
			</u-form-item>

			<button class="submit" @click="submitForm">提交</button>
		</u-form>
	</view>
</template>

<script>
	import {
		editPassword
	} from "@/api/me"; // 假设 editPassword 是后端接口方法

	export default {
		data() {
			return {
				form: {
					oldPassword: '',
					newPassword: '',
					confirmNewPassword: ''
				}
			};
		},
		methods: {
			async submitForm() {
				// 先校验两次新密码是否一致
				if (this.form.newPassword !== this.form.confirmNewPassword) {
					uni.showToast({
						title: "新密码不一致",
						icon: "error"
					});
					return;
				}
				try {
					// 调用后端接口，传递旧密码和新密码
					const response = await editPassword({
						oldPassword: this.form.oldPassword,
						newPassword: this.form.newPassword
					});
					if (response.data.code === 200) {
						uni.showToast({
							title: "密码修改成功",
							icon: "success",
							duration: 2000,
							success: () => {
								setTimeout(() => {
									uni.switchTab({
										url: "/pages/me/me" // tabBar 页面的路径
									});
								}, 1000);
							}
						});
					} else {
						// 处理接口返回的错误信息
						uni.showToast({
							title: response.data.msg || "密码修改失败",
							icon: "error",
							duration: 2000,
						});
					}
				} catch (error) {
					console.error(error);
					uni.showToast({
						title: response.data.msg || "请求出错，请重试",
						icon: "error"
					});
				}
			}
		}
	};
</script>

<style scoped>
	.edit-password {
		padding: 20px;
	}

	.submit {
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