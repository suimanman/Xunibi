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
					<u-cell :title="`列表长度-${index + 1}`">
						<u-avatar slot="icon" shape="square" size="35" :src="item.url"
							customStyle="margin: -3px 5px -3px 0"></u-avatar>
					</u-cell>
				</u-list-item>
			</u-list>
		</view>
	</view>
</template>

<script>
	import { isLogin } from '@/api/me.js';
import {
		getNotice,
		getCoin
	} from '@/api/home';
	export default {
		data() {
			return {
				notice:'',
				virtualCoins: '',
				signInStatus: "未签到",
				indexList: [],
				urls: [
					'https://cdn.uviewui.com/uview/album/1.jpg',
					'https://cdn.uviewui.com/uview/album/2.jpg',
					'https://cdn.uviewui.com/uview/album/3.jpg',
					'https://cdn.uviewui.com/uview/album/4.jpg',
					'https://cdn.uviewui.com/uview/album/5.jpg',
					'https://cdn.uviewui.com/uview/album/6.jpg',
					'https://cdn.uviewui.com/uview/album/7.jpg',
					'https://cdn.uviewui.com/uview/album/8.jpg',
					'https://cdn.uviewui.com/uview/album/9.jpg',
					'https://cdn.uviewui.com/uview/album/10.jpg',
				]
			};
		},
		created() {
			this.getNotice();
			if(isLogin())
			{
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
</style>