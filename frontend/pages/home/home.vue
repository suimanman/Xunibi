<template>
  <view class="home-page">
	  <!-- 公告栏/通知 -->
	  <u-notice-bar text="最新公告：下周三将举行团队培训，请务必参加！" />
    <!-- 欢迎消息与用户状态 -->
    <view class="welcome-section">
      <u-avatar src="static/image/team-logo.png" size="35"></u-avatar>
      <view class="info">
        <text>团队虚拟币余额: {{virtualCoins}}</text>
      </view>
	  <view>
		  <u-button text="签到" type="primary" size="small" custom-style="width: 10px;"></u-button>
	  </view>
    </view>

    <!-- 活动记录 -->
    <u-divider content-position="left">最近活动</u-divider>
    <view class="u-page">
		<text>虚拟币交易记录</text>
    		<u-list
    			@scrolltolower="scrolltolower"
    		>
    			<u-list-item
    				v-for="(item, index) in indexList"
    				:key="index"
    			>
    				<u-cell
    					:title="`列表长度-${index + 1}`"
    				>
    					<u-avatar
    						slot="icon"
    						shape="square"
    						size="35"
    						:src="item.url"
    						customStyle="margin: -3px 5px -3px 0"
    					></u-avatar>
    				</u-cell>
    			</u-list-item>
    		</u-list>
    	</view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      teamName: "创新团队",
      virtualCoins: 500,
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
  onLoad() {
  			this.loadmore()
  		},
  methods: {
	  scrolltolower() {
	  				this.loadmore()
	  			},
		loadmore() {
	  				for (let i = 0; i < 30; i++) {
	  					this.indexList.push({
	  						url: this.urls[uni.$u.random(0, this.urls.length - 1)]
	  					})
	  				}
	  			},
  },
};
</script>

<style scoped>
.home-page {
  padding: 10px;
}

.welcome-section {
  background-color: white; /* 背景色 */
  border-radius: 15px; /* 通过 border-radius 实现椭圆效果 */
  text-align: center;
  display: flex;
  justify-content: center;
  display: flex;
  align-items: center;
  padding: 20px 0;
}

.info{
	font-size: 15px;
	margin-left: 10px;
	padding: 10px;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.quick-actions {
  margin-top: 20px;
}

.activity-list {
  margin-top: 20px;
}
.u-list-item{
	background-color: white; /* 背景色 */
	border-radius: 15px; /* 通过 border-radius 实现椭圆效果 */
}
</style>