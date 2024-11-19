<template>
	<view class="container">
		<u-toast ref="uToast"></u-toast>
		<view class="header">
			<text class="required">*</text>
			<text class="section-title">申请陈述</text>
		</view>
		<view class="statement">
			<u--textarea v-model="statementInfo.info" placeholder="请输入内容" count></u--textarea>
		</view>
		<!-- 底部按钮 -->
		<view class="bottom-buttons">
			<view class="back-button">
				<u-button :plain="true" @click="goBack">返回</u-button>
			</view>
			<view class="submit-button">
				<u-button type="primary" :plain="true" @click="submitInfo">保存</u-button>
			</view>
		</view>
		<view class="hint" v-if="showHint">
			<text class="hintText required">"申请陈述"为必填项</text>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				params: {
					type: 'success',
					message: "保存成功！",
				},
				showHint: false,
				statementInfo: {
					info: ''
				}
			}
		},
		onLoad: function(option) {
			const eventChannel = this.getOpenerEventChannel();
			// 接收上级页面传递的数据
			eventChannel.on('updateStatement', (data) => {
				// console.log('接收到上级页面传递的数据：', data);
				this.statementInfo = {
					...data.data // 注意解构 data.data
				};
			});
		},
		methods: {
			submitInfo() {
				console.log("申请陈述：", this.statementInfo.info);
				// 检查 statementInfo.info 是否为空或仅包含空格
				if (!this.statementInfo.info || !this.statementInfo.info.trim()) {
					this.showHint = true; // 显示提示
					return; // 阻止提交
				}

				// 返回主页面并传递数据
				const eventChannel = this.getOpenerEventChannel();
				eventChannel.emit('acceptStatement', {
					data: this.statementInfo
				});
				this.showToast(this.params);
				// 返回主页面
			},
			goBack() {
				uni.navigateBack();
			},
			showToast(params) {
				this.$refs.uToast.show({
					...params,
					complete() {
						uni.navigateBack();
					}
				})
			}
		}
	}
</script>

<style lang="scss">
	.container {
		margin: 20px;
	}

	.statement {
		margin-top: 10px;
	}

	.required {
		color: red;
	}

	.section-title {
		font-size: 14px;
		font-weight: bold;
	}

	.bottom-buttons {
		position: fixed;
		bottom: 0;
		height: 65px;
		left: 0;
		width: 100%;
		display: flex;
		justify-content: space-between;
		padding: 10px 10px;
		/* 可以调整间距 */
		background-color: white;
		/* 背景色，根据需要调整 */
		box-shadow: 0 -4px 8px rgba(0, 0, 0, 0.1);
	}

	.back-button {
		flex: 2;
		padding-right: 10px;
	}

	.submit-button {
		flex: 8;
		padding-right: 20px;
		font-size: 16px;
		border-radius: 5px;
	}

	.hint {
		margin-top: 10px;
	}

	.hintText {
		font-size: 12px;
	}
</style>