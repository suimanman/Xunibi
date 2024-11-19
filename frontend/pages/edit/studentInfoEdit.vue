<template>
	<view class="infoEditContainer">
		<view class="studentInfo">
			<u--form labelPosition="left" :model="model1" :rules="rules" ref="uForm">
				<u-form-item label="姓名" prop="studentInfo.name" borderBottom ref="item1">
					<u--input v-model="studentInfo.name" border="none" placeholder="请输入姓名"></u--input>
				</u-form-item>
				<u-form-item label="学号" prop="studentInfo.id" borderBottom ref="item1">
					<u--input v-model="studentInfo.id" border="none" placeholder="请输入学号"></u--input>
				</u-form-item>
			</u--form>
		</view>
		<view class="studentInfo">
			<u--form labelPosition="left" :model="model1" :rules="rules" ref="uForm">
				<u-form-item label="院系" prop="studentInfo.department" borderBottom ref="item1">
					<u--input v-model="studentInfo.department" border="none" placeholder="请输入所属学院"></u--input>
				</u-form-item>
				<u-form-item label="专业" prop="studentInfo.major" borderBottom ref="item1">
					<u--input v-model="studentInfo.major" border="none" placeholder="请输入所属专业"></u--input>
				</u-form-item>
				<u-form-item label="班级" prop="studentInfo.class" borderBottom ref="item1">
					<u--input v-model="studentInfo.class" border="none" placeholder="请输入所属班级"></u--input>
				</u-form-item>
				<u-form-item label="所属团队" prop="studentInfo.team" borderBottom ref="item1" label-width="100px">
					<u--input v-model="studentInfo.team" border="none" placeholder="请输入所属团队"></u--input>
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
				studentInfo: {
					name: '',
					id: '',
					department: '',
					major: '',
					class: '',
					team: ''
				},
			};
		},
		onLoad: function(option) {
			const eventChannel = this.getOpenerEventChannel();
			// 接收上级页面传递的数据
			eventChannel.on('updateStudentInfo', (data) => {
				// console.log('接收到上级页面传递的数据：', data);
				this.studentInfo = {
					...data.data // 注意解构 data.data
				};
			});
		},

		methods: {
			submitInfo() {
				// 返回主页面并传递数据
				const eventChannel = this.getOpenerEventChannel();
				eventChannel.emit('acceptStudentInfo', {
					data: this.studentInfo
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