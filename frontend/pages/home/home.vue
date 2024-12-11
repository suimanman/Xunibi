<template>
	<view class="home-page">
		<!-- 公告栏/通知 -->
		<u-notice-bar :text="formattedNotice" />

		<!-- 欢迎消息与用户状态 -->
		<view class="welcome-section">
			<view class="info">
				<text>团队虚拟币余额: {{ virtualCoins }}</text>
			</view>
			<!-- <view class="button-container">
				<u-icon name="checkmark-circle" size="30" color="#5677fc" @click="signIn"></u-icon>
				<text class="sign-text">全部交易记录</text>
			</view> -->
		</view>

		<!-- 活动记录 -->
		<u-divider content-position="left">最近活动</u-divider>
		<view class="transaction-section">
			<view class="transaction-header">
				<u-icon name="level" size="20" color="#5677fc"></u-icon>
				<text class="transaction-text">本月虚拟币交易记录</text>
				<text class="all" @click="getAll">全部</text>
			</view>
			<scroll-view class="transaction-list" scroll-y @scrolltolower="scrolltolower">
				<u-list>
					<u-list-item v-for="(item, index) in filteredList" :key="index">
						<u-cell>
							<view slot="title" class="transaction-content">
								<view class="left-section">
									<text class="description">{{ item.description }}</text> -
									<text class="type">{{ item.type }}</text>
								</view>
								<view class="right-section">
									<text class="amount"
										:class="{ 'income': item.type === '收入', 'expense': item.type === '支出' }">
										{{ item.type === '收入' ? '+' : '-' }}{{ item.amount }}
									</text>
									<text class="date">{{ item.date }}</text>
								</view>
							</view>
						</u-cell>
					</u-list-item>
				</u-list>
			</scroll-view>
		</view>
	</view>
</template>

<script>
	import {
		isLogin
	} from '@/api/me.js';
	import {
		getNotice,
		getCoin,
		getRecords
	} from '@/api/home';
	export default {
		data() {
			return {
				notice: [],
				formattedNotice: "",
				virtualCoins: '',
				signInStatus: "未签到",
				indexList: [], // 原始记录数据
				filteredList: [], // 筛选后的数据
				filterDate: '' // 筛选日期
			};
		},
		created() {
			this.getNotice();
			if (isLogin()) {
				this.getCoin();
				this.getRecords();
			}
		},
		// 下拉刷新
		onPullDownRefresh() {
			this.getNotice();
			this.getCoin();
			this.getRecords();

			setTimeout(() => {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		methods: {
			scrolltolower() {
				this.loadmore();
			},
			getAll() {
				uni.navigateTo({
					url: '/pages/home/record',
				});
			},
			async getNotice() {
				try {
					const result = await getNotice();
					this.notice = result.data.data;
					this.formatNotices();
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
			},
			async getRecords() {
				try {
					const result = await getRecords();
					// 获取当前月份
					const now = new Date();
					const currentYear = now.getFullYear();
					const currentMonth = now.getMonth(); // 注意：getMonth() 返回的是 0~11

					// 将数据映射到 indexList 格式，并筛选出当前月份的数据
					this.indexList = result.data.data
						.map(item => ({
							description: item.description,
							type: item.transactionType === '收入' ? '收入' : '支出',
							amount: item.coinAmount,
							date: item.transactionDate
						}))
						.filter(item => {
							const itemDate = new Date(item.date);
							return (
								itemDate.getFullYear() === currentYear &&
								itemDate.getMonth() === currentMonth
							);
						})
						.sort((a, b) => new Date(b.date) - new Date(a.date)); // 按日期倒序排序

					this.filteredList = [...this.indexList]; // 默认展示当前月份的数据
				} catch (error) {
					console.error('方法异常！', error);
				}
			},
			// 格式化通知数组
			formatNotices() {
				this.formattedNotice = this.notice.join("     "); // 使用多个空格分隔通知
			}
		}
	};
</script>

<style scoped lang="scss">
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

	.filter-section {
		margin: 10px 0;
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

	.all {
		font-size: 14px;
		color: #333;
		font-weight: bold;
		margin-left: 140px;
	}

	.transaction-list {
		max-height: 660px;
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

	.right-section {
		display: flex;
		align-items: center;
		margin-left: auto;
	}

	.amount {
		font-size: 14px;
		font-weight: bold;
		display: flex;
		width: 60px;
		margin-left: 20px;
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