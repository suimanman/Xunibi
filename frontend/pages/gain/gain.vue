<template>
	<view class="container">

		<!-- <view class="search-bar">
			<u-search placeholder="找应用" @input="onSearchInput" />
		</view> -->

		<u-swiper :list="list2" keyName="image" showTitle :autoplay="false" circular></u-swiper>

		<text class="section-title">打卡</text>
		<!-- Section: 打卡 -->
		<view class="section">
			<u-grid col="4" :border="false" class="grid">
				<u-grid-item @click="navigateTo('assistantHelper')" class="grid-item">
					<image src="@/static/icon/签到.png" class="icon"></image>
					<text class="icon-text">签到</text>
				</u-grid-item>
				<u-grid-item @click="navigateTo('scholarshipHelper')" class="grid-item">
					<image src="@/static/icon/值班.png" class="icon"></image>
					<text class="icon-text">值班</text>
				</u-grid-item>
			</u-grid>
		</view>

		<text class="section-title">成果</text>
		<!-- Section: 成果 -->
		<view class="section">
			<u-grid col="4" :border="false" class="grid">
				<u-grid-item @click="navigateTo('myPosition')" class="grid-item">
					<image src="@/static/icon/EC_个人文稿-学术论文.png" class="icon"></image>
					<text class="icon-text">论文</text>
				</u-grid-item>
				<u-grid-item @click="navigateTo('studentNotice')" class="grid-item">
					<image src="/static/icon/专利申请.png" class="icon"></image>
					<text class="icon-text">专利</text>
				</u-grid-item>
				<u-grid-item @click="navigateTo('positionMarket')" class="grid-item">
					<image src="/static/icon/知识竞赛.png" class="icon"></image>
					<text class="icon-text">竞赛</text>
				</u-grid-item>
			</u-grid>
		</view>

		<text class="section-title">培训</text>
		<!-- Section: 培训 -->
		<view class="section">
			<u-grid col="4" :border="false" class="grid">
				<u-grid-item @click="navigateTo('scholarshipApplication')" class="grid-item">
					<image src="/static/icon/培训活动、动员大会.png" class="icon"></image>
					<text class="icon-text">校内外培训</text>
				</u-grid-item>
				<u-grid-item @click="navigateTo('ethnicMinorities')" class="grid-item">
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
					image: 'https://cdn.uviewui.com/uview/swiper/swiper2.png',
					title: '昨夜星辰昨夜风，画楼西畔桂堂东',
				}, {
					image: 'https://cdn.uviewui.com/uview/swiper/swiper1.png',
					title: '身无彩凤双飞翼，心有灵犀一点通'
				}, {
					image: 'https://cdn.uviewui.com/uview/swiper/swiper3.png',
					title: '谁念西风独自凉，萧萧黄叶闭疏窗，沉思往事立残阳'
				}],
			}
		},
		methods: {
			goBack() {
				uni.navigateBack();
			},
			navigateTo(page) {
				uni.navigateTo({
					url: `/pages/${page}/${page}`
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
</style>