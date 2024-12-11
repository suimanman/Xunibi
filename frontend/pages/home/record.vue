<template>
	<view>
		<view class="transaction-section">
			<view class="transaction-header">
				<u-icon name="level" size="20" color="#5677fc"></u-icon>
				<text class="transaction-text">虚拟币交易记录</text>
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
		getRecords
	} from '@/api/home';
	export default {
		data() {
			return {
				notice: '',
				virtualCoins: '',
				signInStatus: "未签到",
				indexList: [], // 原始记录数据
				filteredList: [], // 筛选后的数据
				// filterDate: '' // 筛选日期
			};
		},
		created() {
			this.getRecords();
		},
		// 下拉刷新
		onPullDownRefresh() {
			this.getRecords();

			setTimeout(() => {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		methods: {
			scrolltolower() {
				this.loadmore();
			},
			async getRecords() {
				try {
					const result = await getRecords();
					// 将数据映射到 indexList 格式，并按日期倒序排序
					this.indexList = result.data.data.map(item => ({
						description: item.description,
						type: item.transactionType === '收入' ? '收入' : '支出',
						amount: item.coinAmount,
						date: item.transactionDate
					})).sort((a, b) => new Date(b.date) - new Date(a.date)); // 按日期倒序排序
					this.filteredList = [...this.indexList]; // 默认展示所有数据
				} catch (error) {
					console.error('方法异常！', error);
				}
			},
		}
	};
</script>

<style lang="scss" scoped>
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