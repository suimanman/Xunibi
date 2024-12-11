<template>
	<view class="infoEditContainer">
		<view class="studentInfo">
			<u--form labelPosition="left" :model="model1" :rules="rules" ref="uForm">
				<u-form-item label="姓名" prop="studentInfo.name" borderBottom ref="item1">
					<u--input v-model="studentInfo.name" border="none" placeholder="请输入姓名"></u--input>
				</u-form-item>
				<u-form-item label="学号" prop="studentInfo.username" borderBottom ref="item1">
					<text>{{studentInfo.username}}</text>
				</u-form-item>
				<u-form-item label="团队" prop="studentInfo.teamName" borderBottom ref="item1">
					<u--input v-model="studentInfo.teamName" border="none" placeholder="请输入所属团队"></u--input>
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
				<u-form-item label="班级" prop="studentInfo.clazz" borderBottom ref="item1">
					<u--input v-model="studentInfo.clazz" border="none" placeholder="请输入所属班级"></u--input>
				</u-form-item>
			</u--form>
		</view>

		<button class="submit-button" @click="submitInfo">保存</button>
	</view>
</template>

<script>
	import {
		editElseInfo,
		isLogin
	} from '../../api/me';
	export default {
		data() {
			return {
				studentInfo: {
					name: '',
					username: '',
					department: '',
					major: '',
					clazz: '',
					teamName: ''
				},
			};
		},
		created() {
			this.loginHandle();
		},
		methods: {
			async loginHandle() {
				// 调用 isLogin 并等待其返回结果
				const loginResult = await isLogin();
				// 获取用户数据
				// 定义允许的字段
				const allowedFields = Object.keys(this.studentInfo);
				
				// 筛选后端返回的数据
				this.studentInfo = Object.keys(loginResult.data.data)
				    .filter(key => allowedFields.includes(key)) // 筛选只保留前端需要的字段
				    .reduce((obj, key) => {
				        obj[key] = loginResult.data.data[key]; // 构建新对象
				        return obj;
				    }, {});
				console.log(this.studentInfo);
			},
			submitInfo() {
				const hasEmptyField = Object.values(this.studentInfo).some(
					(value) => value === null || value === undefined || !String(value).trim()
				);
				console.log(this.studentInfo);
				if (hasEmptyField) {
					uni.showToast({
						title: '请填写所有必填字段',
						icon: 'none',
						duration: 2000,
					});
					return;
				}

				// 如果所有字段都填写，提交信息
				editElseInfo(this.studentInfo)
					.then(() => {
						uni.showToast({
							title: '保存成功',
							icon: 'success',
							duration: 2000,
						});
						uni.navigateBack(); // 返回主页面
					})
					.catch((error) => {
						uni.showToast({
							title: '该团队未注册，请重新填写！',
							icon: 'none',
							duration: 2000,
						});
						console.error('提交失败:', error);
					});
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