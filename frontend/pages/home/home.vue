<template>
	<view class="home-page">
		<!-- 公告栏/通知 -->
		<u-notice-bar :text="notice" />
		
		<!-- 欢迎消息与用户状态 -->
		<view class="welcome-section">
			<view class="info">
				<text>团队虚拟币余额: {{ virtualCoins }}</text>
			</view>
			<view class="button-container">
				<u-icon name="checkmark-circle" size="30" color="#5677fc" @click="signIn"></u-icon>
				<text class="sign-text">签到</text>
			</view>
		</view>
		
		<!-- 活动记录 -->
		<u-divider content-position="left">最近活动</u-divider>
		<view class="transaction-section">
			<view class="transaction-header">
				<u-icon name="level" size="20" color="#5677fc"></u-icon>
				<text class="transaction-text">虚拟币交易记录</text>
			</view>
			<scroll-view class="transaction-list" scroll-y @scrolltolower="scrolltolower">
				<u-list>
					<u-list-item v-for="(item, index) in indexList" :key="index">
						<u-cell>
							<view slot="title" class="transaction-content">
								<view class="left-section">
									<text class="description">{{ item.description }}</text> -
									<text class="type">{{ item.type }}</text>
								</view>
								<text class="amount" :class="{ 'income': item.type === '收入', 'expense': item.type === '支出' }">
									{{ item.type === '收入' ? '+' : '-' }}{{ item.amount }}
								</text>
								<text class="date">{{ item.date }}</text>
							</view>
						</u-cell>
					</u-list-item>
				</u-list>
			</scroll-view>
		</view>
	</view>
</template>

<script>
	import { isLogin } from '@/api/me.js';
	import { getNotice, getCoin } from '@/api/home';
	export default {
		data() {
			return {
				notice: '',
				virtualCoins: '',
				signInStatus: "未签到",
				indexList: [
					{
						description: "购买设备",
						type: "支出",
						amount: 20,
						date: "2024-10-29"
					},
					{
						description: "团队奖励",
						type: "收入",
						amount: 40,
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
		methods: {
			scrolltolower() {
				this.loadmore();
			},
			async getNotice() {
				try {
					const result = await getNotice();
					this.notice = result.data.msg;
				} catch (error) {
					console.error('方法异常！', error);
				}
			},
			async getCoin() {
				try {
					const result = await getCoin();
					this.virtualCoins = result.data.data;
				} catch (error) {
					console.error('方法异常！', error);
				}
			}
		}
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

	.transaction-section {
		margin-top: 20px;
	}

	.transaction-header {
		display: flex;
		align-items: center;
		padding: 10px;
		border-radius: 8px;
		margin-bottom: 10px;
	}

	.transaction-text {
		font-size: 16px;
		font-weight: bold;
		color: #333;
		margin-left: 8px;
	}

	.transaction-list {
		height: 400px; /* 根据需要调整高度 */
		overflow: auto;
	}

	.transaction-content {
		display: flex;
		justify-content: space-between;
		align-items: center;
		width: 100%;
	}

	.left-section {
		display: flex;
		flex-shrink: 0;
		color: #333;
		font-size: 14px;
	}

	.amount {
		font-size: 14px;
		font-weight: bold;
		flex-shrink: 0;
		margin: 0 10px;
	}

	.type {
		color: #999;
		margin-left: 5px;
	}

	.income {
		color: #4caf50;
	}

	.expense {
		color: #f44336;
	}

	.date {
		color: #666;
		font-size: 13px;
		flex-shrink: 0;
		text-align: right;
	}
</style>