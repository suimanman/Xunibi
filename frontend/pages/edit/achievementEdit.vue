<template>
	<view class="infoEditContainer">
		<u-datetime-picker :show="show" v-model="value1" mode="date" closeOnClickOverlay @confirm="confirm"
			@cancel="cancel" @close="close"></u-datetime-picker>
		<view class="studentInfo">
			<u--form labelPosition="left" :model="model1" :rules="rules" ref="uForm">
				<u-form-item label="成果名称" prop="achievementInfo.name" borderBottom ref="item1" label-width="100px">
					<u--input v-model="achievementInfo.name" border="none" placeholder="请输入"></u--input>
				</u-form-item>
				<u-form-item label="获得时间" prop="achievementInfo.dateValue" borderBottom ref="item1" label-width="100px">
					<view class="date-picker-container" @click="show = true">
						<text v-if="!achievementInfo.dateValue" class="placeholder">请选择</text>
						<text v-else>{{ achievementInfo.dateValue }}</text>
						<image src="@/static/icon/修改.png" class="icon-edit"></image>
					</view>
				</u-form-item>
				<u-form-item label="颁发单位" prop="achievementInfo.awardUnit" borderBottom ref="item1" label-width="100px">
					<u--input v-model="achievementInfo.awardUnit" border="none" placeholder="请输入"></u--input>
				</u-form-item>
			</u--form>
		</view>
		<button class="submit-button" @click="submitInfo">确定</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				show: false,
				value1: Number(new Date()), // 初始化为当前时间的时间戳
				achievementInfo: {
					name: '',
					dateValue: '',
					awardUnit: ''
				}
			};
		},
		onLoad: function(option) {
			const eventChannel = this.getOpenerEventChannel();
			// 接收上级页面传递的数据
			eventChannel.on('updateAchievement', (data) => {
				// console.log('接收到上级页面传递的数据：', data);
				this.achievementInfo = {
					...data.data // 注意解构 data.data
				};
			});
		},
		methods: {
			submitInfo() {
				// 返回主页面并传递数据
				const eventChannel = this.getOpenerEventChannel();
				eventChannel.emit('acceptAchievement', {
					data: this.achievementInfo
				});
			
				// 返回主页面
				uni.navigateBack();
			},
			confirm(e) {
			    const timestamp = e.value; // 获取当前所选的时间戳
			    const date = new Date(timestamp); // 转换为 Date 对象
			    
			    // 格式化日期为 YYYY-MM-DD
			    const year = date.getFullYear();
			    const month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份补零
			    const day = date.getDate().toString().padStart(2, '0'); // 日补零
			    
			    this.achievementInfo.dateValue = `${year}-${month}-${day}`; // 格式化后的日期
			    this.show = false; // 关闭日期选择器
			    
			    // console.log("确认时间（格式化后）：", this.achievementInfo.dateValue);
			},
			close() {
				this.show = false; // 关闭日期选择器
			},
			cancel() {
				this.show = false; // 关闭日期选择器
			},
		},
	};
</script>

<style lang="scss">
	.infoEditContainer {
		margin: 20px;
	}

	.studentInfo {
		margin-top: 10px;
		background-color: white;
		border-radius: 10px;
		padding: 10px 20px;
	}

	.date-picker-container {
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 10px 0;
	}

	.placeholder {
		color: #C1C4CB;
		font-size: 15px;
	}

	.icon-edit {
		width: 20px;
		height: 20px;
		margin-left: 10px;
	}

	.submit-button {
		margin-top: 30px;
		font-size: 28rpx;
		background: #5677fc;
		color: #fff;
		height: 90rpx;
		line-height: 90rpx;
		border-radius: 50rpx;
		box-shadow: 0 5px 7px 0 rgba(86, 119, 252, 0.2);
	}
</style>