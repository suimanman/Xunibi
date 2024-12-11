<template>
	<view class="infoEditContainer">
		<view class="studentInfo">
			<u--form labelPosition="left" :model="model1" :rules="rules" ref="uForm">
				<u-form-item label="姓名" prop="dutyInfo.name" borderBottom ref="item1">
					<u--input v-model="dutyInfo.name" border="none" placeholder="请输入姓名"></u--input>
				</u-form-item>
				<u-form-item label="学号" prop="dutyInfo.id" borderBottom ref="item1">
					<u--input v-model="dutyInfo.id" border="none" placeholder="请输入学号"></u--input>
				</u-form-item>
				<u-form-item label="所属团队" prop="dutyInfo.team" borderBottom ref="item1" label-width="100px">
					<u--input v-model="dutyInfo.team" border="none" placeholder="请输入所属团队"></u--input>
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
				dutyInfo: {
					name: '',
					id: '',
					team: '',
					image: '',
				},
			};
		},
		onLoad: function(option) {
			const eventChannel = this.getOpenerEventChannel();
			// 接收上级页面传递的数据
			eventChannel.on('updateDutyInfo', (data) => {
				// console.log('接收到上级页面传递的数据：', data);
				this.dutyInfo = {
					...data.data // 注意解构 data.data
				};
			});
		},

		methods: {
			submitInfo() {
				// 返回主页面并传递数据
				const eventChannel = this.getOpenerEventChannel();
				eventChannel.emit('acceptDutyInfo', {
					data: this.dutyInfo
				});

				// 返回主页面
				uni.navigateBack();
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

	.submit-button {
		margin-top: 30px;
		/* 调整按钮的顶部间距 */
		font-size: 28rpx;
		background: #5677fc;
		color: #fff;
		height: 90rpx;
		line-height: 90rpx;
		border-radius: 50rpx;
		box-shadow: 0 5px 7px 0 rgba(86, 119, 252, 0.2);
	}
</style>