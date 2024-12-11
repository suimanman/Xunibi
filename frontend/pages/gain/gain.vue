<template>
	<view class="container">

		<!-- <image class="img-b" src="https://zhoukaiwen.com/img/loginImg/3.png"></image> -->
		<!-- <view class="search-bar">
			<u-search placeholder="找应用" @input="onSearchInput" />
		</view> -->

		<u-swiper :list="list2" keyName="image" showTitle :autoplay="false" circular></u-swiper>
		
		<text class="section-title">值日</text>
		<!-- Section: 打卡 -->
		<view class="section">
			<u-grid col="4" :border="false" class="grid">
				<!-- <u-grid-item @click="navigateTo('assistantHelper')" class="grid-item">
					<image src="@/static/icon/签到.png" class="icon"></image>
					<text class="icon-text">签到</text>
				</u-grid-item> -->
				<u-grid-item @click="navigateToDuty" class="grid-item">
					<image src="@/static/icon/值班.png" class="icon"></image>
					<text class="icon-text">值班</text>
				</u-grid-item>
			</u-grid>
		</view>

		<text class="section-title">成果</text>
		<!-- Section: 成果 -->
		<view class="section">
			<u-grid col="4" :border="false" class="grid">
				<u-grid-item @click="navigateToPaper" class="grid-item">
					<image src="@/static/icon/EC_个人文稿-学术论文.png" class="icon"></image>
					<text class="icon-text">论文</text>
				</u-grid-item>
				<u-grid-item @click="navigateToPatent" class="grid-item">
					<image src="/static/icon/专利申请.png" class="icon"></image>
					<text class="icon-text">专利</text>
				</u-grid-item>
				<u-grid-item @click="navigateToCompetition" class="grid-item">
					<image src="/static/icon/知识竞赛.png" class="icon"></image>
					<text class="icon-text">竞赛</text>
				</u-grid-item>
			</u-grid>
		</view>

		<text class="section-title">培训</text>
		<!-- Section: 培训 -->
		<view class="section">
			<u-grid col="4" :border="false" class="grid">
				<u-grid-item @click="navigateToTrain" class="grid-item">
					<image src="/static/icon/培训活动、动员大会.png" class="icon"></image>
					<text class="icon-text">校内外培训</text>
				</u-grid-item>
				<u-grid-item @click="navigateToMeeting" class="grid-item">
					<image src="/static/icon/会议管理.png" class="icon"></image>
					<text class="icon-text">会议参加</text>
				</u-grid-item>
			</u-grid>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				list2: [{
					image: '/static/img/1.png',
					title: '河北工业大学——众创空间',
				}],
			}
		},
		methods: {
			goBack() {
				uni.navigateBack();
			},
			navigateToDuty() {
				uni.navigateTo({
					url: '/pages/gain/duty',
				});
			},
			navigateToPaper() {
				uni.navigateTo({
					url: '/pages/gain/achievementSubmit',
					success: (res) => {
						// 通过 eventChannel 向被打开页面传送数据
						res.eventChannel.emit('submit', {
							data: '论文'
						});
					},
				});

			},
			navigateToPatent() {
				uni.navigateTo({
					url: '/pages/gain/achievementSubmit',
					success: (res) => {
						// 通过 eventChannel 向被打开页面传送数据
						res.eventChannel.emit('submit', {
							data: '专利'
						});
					},
				});

			},
			navigateToCompetition() {
				uni.navigateTo({
					url: '/pages/gain/achievementSubmit',
					success: (res) => {
						// 通过 eventChannel 向被打开页面传送数据
						res.eventChannel.emit('submit', {
							data: '竞赛'
						});
					},
				});

			},
			navigateToTrain() {
				uni.navigateTo({
					url: '/pages/gain/train',
					success: (res) => {
						// 通过 eventChannel 向被打开页面传送数据
						res.eventChannel.emit('submit', {
							data: '培训'
						});
					},
				});

			},
			navigateToMeeting() {
				uni.navigateTo({
					url: '/pages/gain/train',
					success: (res) => {
						// 通过 eventChannel 向被打开页面传送数据
						res.eventChannel.emit('submit', {
							data: '会议'
						});
					},
				});

			},
			onSearchInput(event) {
				console.log(event.detail.value);
			}
		}
	};
</script>

<style scoped>
	.container {
		padding: 10px;
	}

	.search-bar {
		margin: 10px 0;
	}

	.section-title {
		font-size: 14px;
		color: #666666;
		padding-left: 5px;
		margin-bottom: 10px;
	}

	.section {
		height: 90px;
		/* 可以根据需要调整高度 */
		margin: 10px;
		background-color: white;
		border-radius: 8px;
		display: flex;
		align-items: center;
		/* 垂直居中 */
		justify-content: center;
		/* 水平居中 */
	}

	.grid {
		width: 100%;
		/* 确保 grid 占满 section 宽度 */
	}

	.grid-item {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		background-color: white;
		border-radius: 8px;
		padding: 10px;
	}

	.icon {
		width: 33px;
		height: 33px;
		margin-bottom: 5px;
	}

	.icon-text {
		font-size: 14px;
		color: #333333;
	}

	.img-b {
		position: absolute;
		width: 50%;
		bottom: 0;
		left: -50rpx;
		/* margin-bottom: -200rpx; */
	}
</style>