<template>
	<view class="container">
		<u-toast ref="uToast"></u-toast>
		<!-- 顶部 Banner 和金额信息 -->
		<view class="header-banner">
			<image src="/static/img/背景图片.png" class="background-image" mode="aspectFill"></image>
			<!-- 其他内容 -->
			<text class="banner-info">{{achievementInfo.type}}参加情况提交</text>
			<text class="banner-coin">获取虚拟币</text>
		</view>

		<view class="info">
			<!-- 学生基本信息 -->
			<view class="section">
				<view class="section-header">
					<text class="section-title">学生基本信息</text>
					<text class="more-button" @click="editStudentInfo">更多</text>
				</view>
				<view class="section-content">
					<text class="line">姓名：{{studentInfo.name}}</text>
					<text class="line">学号：{{studentInfo.id}}</text>
					<text class="line">所属团队：{{studentInfo.team}}</text>
					<text class="line">
						院系班：{{ studentInfo.department }}{{ studentInfo.department && studentInfo.major ? '/' : '' }}{{ studentInfo.major }}{{ (studentInfo.department || studentInfo.major) && studentInfo.clazz ? '/' : '' }}{{ studentInfo.clazz }}
					</text>
				</view>
			</view>

			<!-- 学年期间主要获奖情况 -->
			<view class="section">
				<view class="section-header">
					<text class="section-title">{{achievementInfo.type}}参加基本信息</text>
					<text class="more-button" @click="editAchievement">更多</text>
				</view>
				<view class="section-content">
					<text>包含名称、日期等信息</text>
				</view>
			</view>

			<!-- 申请材料 -->
			<view class="section">
				<view class="section-header">
					<view>
						<text class="required">*</text>
						<text class="section-title">申请陈述</text>
					</view>

					<text class="more-button" @click="editStatement">填写</text>
				</view>
				<view class="section-content">
					<text>包含申请陈述、佐证信息</text>
				</view>
			</view>

			<!-- 底部按钮 -->
			<view class="bottom-buttons">
				<view class="back-button">
					<u-button :plain="true" @click="goBack">返回</u-button>
				</view>
				<view class="submit-button">
					<u-button type="primary" :plain="true" @click="submitAll">提交</u-button>
				</view>

			</view>

		</view>

	</view>
</template>

<script>
	import {
		achievementAward
	} from '../../api/gain';
	import {
		isLogin
	} from '../../api/me';
	export default {
		data() {
			return {
				params: {
					type: 'success',
					message: "提交成功！请等待审核",
				},
				studentInfo: {
					name: '',
					id: '',
					department: '',
					major: '',
					clazz: '',
					team: ''
				},
				achievementInfo: {
					type: '',
					name: '',
					dateValue: '',
					awardUnit: ''
				},
				statementInfo: {
					info: ''
				}
			}
		},
		onLoad: function(option) {
			const eventChannel = this.getOpenerEventChannel();
			// 接收上级页面传递的数据
			eventChannel.on('submit', (data) => {
				// console.log('接收到上级页面传递的数据：', data);
				this.achievementInfo.type = data.data
			});
			console.log("类型：",this.achievementInfo.type)
		},
		methods: {
			goBack() {
				uni.navigateBack();
			},
			submitAll() {
				const description = {
					studentInfo: this.studentInfo,
					achievementInfo: this.achievementInfo,
					statementInfo: this.statementInfo
				};
				if (isLogin()) {
					achievementAward(description);
					this.showToast(this.params);
				}

			},
			showToast(params) {
				this.$refs.uToast.show({
					...params,
					complete() {
						uni.navigateBack();
					}
				})
			},
			editStudentInfo() {
				// console.log("当前传递的数据：", this.studentInfo);
				uni.navigateTo({
					url: '/pages/edit/studentInfoEdit',
					success: (res) => {
						// 通过 eventChannel 向被打开页面传送数据
						res.eventChannel.emit('updateStudentInfo', {
							data: this.studentInfo
						});

						// 接收下级页面返回的数据
						res.eventChannel.on('acceptStudentInfo', (data) => {
							// console.log("接收到下级页面返回的数据：", data);
							this.studentInfo = {
								...data.data // 注意解构 data.data
							};
						});
					},
				});
			},
			editAchievement() {
				uni.navigateTo({
					url: '/pages/edit/trainEdit',
					success: (res) => {
						// 通过 eventChannel 向被打开页面传送数据
						res.eventChannel.emit('updateTrain', {
							data: this.achievementInfo
						});

						// 接收下级页面返回的数据
						res.eventChannel.on('acceptTrain', (data) => {
							// console.log("接收到下级页面返回的数据：", data);
							this.achievementInfo = {
								...data.data // 注意解构 data.data
							};
							console.log("接收的数据:", this.achievementInfo);
						});
					},
				});
			},
			editStatement() {
				uni.navigateTo({
					url: '/pages/edit/statementEdit',
					success: (res) => {
						// 通过 eventChannel 向被打开页面传送数据
						res.eventChannel.emit('updateStatement', {
							data: this.statementInfo
						});

						// 接收下级页面返回的数据
						res.eventChannel.on('acceptStatement', (data) => {
							// console.log("接收到下级页面返回的数据：", data);
							this.statementInfo = {
								...data.data // 注意解构 data.data
							};
							console.log("接收的数据:", this.statementInfo);
						});
					},
				});
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

	.section-content .line {
		display: block;
		/* 每个 <text> 元素独占一行 */
		margin-bottom: 5px;
		/* 可选：增加每行间距 */
		font-size: 14px;
		/* 可选：调整字体大小 */
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
</style>