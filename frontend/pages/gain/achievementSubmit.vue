<template>
	<view class="container">

		<!-- 顶部 Banner 和金额信息 -->
		<view class="header-banner">
			<image src="/static/img/背景图片.png" class="background-image" mode="aspectFill"></image>
			<!-- 其他内容 -->
			<text class="banner-info">论文、专利、竞赛成果提交</text>
			<text class="banner-coin">20虚拟币</text>
		</view>

		<view class="info">
			<!-- 学生基本信息 -->
			<view v-if="showStudentInfo" class="section">
				<view class="section-header">
					<text class="section-title">学生基本信息</text>
					<text class="more-button" @click="navigateToEdit('studentInfo')">更多</text>
				</view>
				<view class="section-content">
					<text>姓名：{name}</text>
					<text>学号：190531</text>
					<text>所在年级：2021级</text>
					<text>院系班：人工智能与数据科学学院/软件工程/软件211</text>
				</view>
			</view>

			<!-- 学年期间主要获奖情况 -->
			<view class="section">
				<view class="section-header">
					<text class="section-title">学年期间主要获奖情况</text>
					<text class="more-button" @click="navigateToEdit('awardsInfo')">更多</text>
				</view>
				<view class="section-content">
					<text>包含奖项名称、获奖日期等信息</text>
				</view>
			</view>

			<!-- 申请材料 -->
			<view class="section">
				<view class="section-header">
					<text class="section-title required">申请材料</text>
					<text class="more-button" @click="navigateToEdit('applicationMaterials')">填写</text>
				</view>
				<view class="section-content">
					<text>包含申请陈述、佐证材料</text>
				</view>
			</view>

			<!-- 底部按钮 -->
			<view class="bottom-buttons">
				<view class="back-button">
					<u-button :plain="true" @click="goBack">返回</u-button>
				</view>
				<view class="submit-button">
					<u-button type="primary" :plain="true">提交</u-button>
				</view>

			</view>

		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				showStudentInfo: false, // 控制学生基本信息部分的显示
			}
		},
		methods: {
			goBack() {
				uni.navigateBack();
			},
			navigateToEdit(page) {
				uni.navigateTo({
					url: `/pages/${page}/${page}`
				});
			}
		},
		mounted() {
			// 检查是否有学生基本信息，如果有则显示
			this.showStudentInfo = this.checkStudentInfo();
		},
		methods: {
			checkStudentInfo() {
				// 这里加入逻辑来检查学生信息是否填写
				// 假设已经填写的信息为true，否则为false
				return true; // 替换为实际检查逻辑
			}
		}
	}
</script>

<style scoped>
	.header-banner {
		height: 110px;
		position: relative;
		/* color: white; */
	}

	.banner-info {
		position: absolute;
		margin-top: 30px;
		margin-left: 35px;
		color: #F5F5F5;
		font-weight: bold;
	}

	.banner-coin {
		position: absolute;
		margin-top: 19px;
		margin-left: 255px;
		font-size: 13px;
		font-weight: bold;
		color: #F5F5F5;
	}

	.background-image {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		z-index: -1;
	}

	.info {
		padding: 20px;
		background-color: #F5F5F5;
	}

	.title {
		font-size: 18px;
		font-weight: bold;
	}

	.amount-badge {
		background-color: #00C853;
		padding: 5px 10px;
		border-radius: 5px;
		font-size: 16px;
	}

	.info-tip {
		background-color: #FFA726;
		padding: 10px;
		border-radius: 5px;
		margin: 10px 0;
		color: white;
	}

	.section {
		background-color: white;
		padding: 15px;
		border-radius: 10px;
		margin-top: 10px;
	}

	.section-header {
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.section-title {
		font-size: 16px;
		font-weight: bold;
	}

	.more-button {
		color: #1E90FF;
		font-size: 14px;
	}

	.section-content {
		margin-top: 5px;
		font-size: 15px;
		color: #666;
	}

	.required {
		color: red;
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
		box-shadow: 0 -4px 8px rgba(0, 0, 0, 0.);
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
</style>