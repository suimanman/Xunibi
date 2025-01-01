<template>
	<view class="edit-password">
		<u-form ref="formRef" :model="form">
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

		<!-- 添加 uToast 组件 -->
		<u-toast ref="uToast" />
	</view>
</template>

<script>
	import {
		editPassword
	} from "@/api/me"; // 假设 editPassword 是后端接口方法

	export default {
		data() {
			return {
				params: {
					type: "success",
					message: "密码修改成功！请重新登录",
				},
				form: {
					oldPassword: "",
					newPassword: "",
					confirmNewPassword: "",
				},
			};
		},
		methods: {
			async submitForm() {
				// 校验两次新密码是否一致
				if (this.form.newPassword !== this.form.confirmNewPassword) {
					uni.showToast({
						title: "新密码不一致",
						icon: "error",
					});
					return;
				}
				try {
					// 调用后端接口，传递旧密码和新密码
					const response = await editPassword({
						oldPassword: this.form.oldPassword,
						newPassword: this.form.newPassword,
					});
					if (response.data.code === 200) {
						this.showToast(this.params);
					} else {
						uni.showToast({
							title: response.data.msg || "密码修改失败",
							icon: "error",
							duration: 2000,
						});
					}
				} catch (error) {
					console.error(error);
					uni.showToast({
						title: error.message || "请求出错，请重试",
						icon: "error",
					});
				}
			},
			showToast(params) {
				this.$refs.uToast.show({
					...params,
					complete() {
						uni.navigateTo({
							url: "/pages/login/login", // tabBar 页面的路径
						});
					},
				});
			},
		},
	};
</script>

<style scoped>
	.edit-password {
		padding: 20px;
	}

	.submit {
		margin-top: 30px;
		font-size: 28rpx;
		background: #5677fc;
		color: #fff;
		height: 90rpx;
		line-height: 90rpx;
		border-radius: 50rpx;
		box-shadow: 0 5px 7px 0 rgba(86, 119, 252, 0.2);
	}
</style>