<template>
	<view class="home-page">
		<!-- 公告栏/通知 -->
		<u-notice-bar :text="notice" />
		<!-- 欢迎消息与用户状态 -->
		<view class="welcome-section">
			<view class="info">
				<text>团队虚拟币余额: {{virtualCoins}}</text>
			</view>
			<view class="button-container">
				<u-icon name="checkmark-circle" size="30" color="#5677fc" @click="signIn"></u-icon>
				<text class="sign-text">签到</text>
			</view>
		</view>

		<!-- 活动记录 -->
		<u-divider content-position="left">最近活动</u-divider>
		<view class="u-page">
			<text>虚拟币交易记录</text>
			<u-list @scrolltolower="scrolltolower">
				<u-list-item v-for="(item, index) in indexList" :key="index">
					<u-cell>
						<view slot="title" class="left-content">
							<view class="left-section">
								<text class="description">{{ item.description }}</text> -
								<text class="type">{{ item.type }}</text>
							</view>
							<text class="date">{{ item.date }}</text>
						</view>
					</u-cell>
				</u-list-item>
			</u-list>
		</view>
	</view>
</template>

<script>
	import {
		isLogin
	} from '@/api/me.js';
	import {
		getNotice,
		getCoin
	} from '@/api/home';
	export default {
		data() {
			return {
				notice: '',
				virtualCoins: '',
				signInStatus: "未签到",
				indexList: [{
						description: "购买设备",
						type: "支出",
						date: "2024-10-29"
					},
					{
						description: "团队奖励",
						type: "收入",
						date: "2024-10-28"
					}
				]
			};
		},
		created() {
			this.getNotice();
			if (isLogin()) {
				this.getCoin();
			}

		},
		onLoad() {
			this.loadmore();
		},
		methods: {
			scrolltolower() {
				this.loadmore();
			},
			loadmore() {
				for (let i = 0; i < 30; i++) {
					this.indexList.push({
						url: this.urls[uni.$u.random(0, this.urls.length - 1)]
					});
				}
			},
			async getNotice() {
				try {
					const result = await getNotice();
					console.log("通知公告：", result.data.msg);
					this.notice = result.data.msg; // 根据实际字段更新
				} catch (error) {
					console.error('方法异常！', error);
				}
			},
			async getCoin() {
				try {
					const result = await getCoin();
					console.log("虚拟币数量：", result.data);
					this.virtualCoins = result.data.data; // 根据实际字段更新
				} catch (error) {
					console.error('方法异常！', error);
				}
			}
		},
	};
</script>

<style scoped>
	.home-page {
		padding: 10px;
	}

	.welcome-section {
		margin-top: 10px;
		background-color: white;
		border-radius: 15px;
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 20px 10px;
	}

	.button-container {
		display: flex;
		flex-direction: column;
		align-items: center;
		margin-right: 10px;
	}

	.info {
		font-size: 15px;
		padding: 10px;
	}

	.quick-actions {
		margin-top: 20px;
	}

	.activity-list {
		margin-top: 20px;
	}

	.u-list-item {
		background-color: white;
		border-radius: 15px;
	}

	.sign-text {
		font-size: 13px;
		margin-top: 2px;
		/* 调整文字和图标的间距 */
	}

	.left-content {
		display: flex;
		justify-content: space-between;
		/* 左右对齐 */
		align-items: center;
		/* 垂直居中 */
		width: 100%;
		/* 占满父容器的宽度 */
	}

	.left-section {
		display: flex;
		flex-shrink: 0;
		/* 防止内容收缩 */
		color: #333;
		font-size: 14px;
	}

	.type {
		color: #999;
		margin-left: 5px;
	}

	.date {
		color: #666;
		font-size: 13px;
		flex-shrink: 0;
		/* 防止日期被挤压 */
		text-align: right;
		/* 确保日期靠右 */
	}
</style>