<template>
	<view class="container">
		<u-toast ref="uToast"></u-toast>
		<!-- 顶部 Banner 和金额信息 -->
		<view class="header-banner">
			<image src="/static/img/背景图片.png" class="background-image" mode="aspectFill"></image>
			<!-- 其他内容 -->
			<text class="banner-info">值日情况提交</text>
			<text class="banner-coin">获取虚拟币</text>
		</view>

		<view class="info">
			<!-- 学生基本信息 -->
			<view class="section">
				<view class="section-header">
					<text class="section-title">值班人信息</text>
					<text class="more-button" @click="editDutyInfo">填写</text>
				</view>
				<view class="section-content">
					<text class="line">姓名：{{dutyInfo.name}}</text>
					<text class="line">学号：{{dutyInfo.username}}</text>
					<text class="line">所属团队：{{dutyInfo.teamName}}</text>
				</view>
			</view>
			<view class="section">
				<view class="section-header">
					<view>
						<text class="section-title">值班情况</text>
					</view>
			
					<text class="more-button" @click="editStatement">填写</text>
				</view>
				<view class="section-content">
					<text>包括值班地点等相关信息(时间默认为提交当天)</text>
				</view>
			</view>
			<!-- 上传照片 -->
			<view class="section">
				<text class="required">*</text>
				<text class="section-title">值日照片上传</text>
				<view>
					<u-upload :fileList="fileList1" @afterRead="afterRead" @delete="deletePic"
						name="1" multiple :maxCount="1" :previewFullImage="true">
						<image class="upload-image" src="@/static/img/图片上传.png"
							mode="widthFix" style="width: 50px;height: 50px;"></image>
							</u-upload>
				</view>
				<text class="beizhu">* 请上传1张大小不超过5M的图片</text>
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
		dutyAward
	} from '../../api/gain';
	import {
		isLogin
	} from '../../api/me';
	export default {
		data() {
			return {
				fileList1: [],
				params: {
					type: 'success',
					message: "提交成功！请等待审核",
				},
				dutyInfo: {
					name: '',
					username: '',
					teamName: '',
					description: '',
					image: '',
				},
				statementInfo: {
					info: ''
				}
			}
		},
		created() {
			this.loginHandle();
		},
		onLoad: function(option) {},
		methods: {
			async loginHandle() {
				// 调用 isLogin 并等待其返回结果
				const loginResult = await isLogin();
				// 获取用户数据
				// 定义允许的字段
				const allowedFields = Object.keys(this.dutyInfo);
			
				// 筛选后端返回的数据
				this.dutyInfo = Object.keys(loginResult.data.data)
					.filter(key => allowedFields.includes(key)) // 筛选只保留前端需要的字段
					.reduce((obj, key) => {
						obj[key] = loginResult.data.data[key]; // 构建新对象
						return obj;
					}, {});
				console.log(this.dutyInfo);
			},
			goBack() {
				uni.navigateBack();
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
							this.dutyInfo.description=this.statementInfo.info;
							console.log("接收的数据:", this.dutyInfo.description);
						});
					},
				});
			},
			// 删除图片
			deletePic(event) {
				this[`fileList${event.name}`].splice(event.index, 1)
			},
			// 新增图片
			async afterRead(event) {
				// 当设置 multiple 为 true 时, file 为数组格式，否则为对象格式
				let lists = [].concat(event.file)
				let fileListLen = this[`fileList${event.name}`].length
				lists.map((item) => {
					this[`fileList${event.name}`].push({
						...item,
						status: 'uploading',
						message: '上传中'
					})
				})
				for (let i = 0; i < lists.length; i++) {
					const result = await this.uploadFilePromise(lists[i].url)
					let item = this[`fileList${event.name}`][fileListLen]
					this[`fileList${event.name}`].splice(fileListLen, 1, Object.assign(item, {
						status: 'success',
						message: '',
						url: result
					}))
					fileListLen++
				}
			},
			uploadFilePromise(url) {
				return new Promise((resolve, reject) => {
					uni.uploadFile({
						url: 'http://localhost:8080/files/upload',
						filePath: url,
						name: 'file',
						formData: {
							user: 'test'
						},
						success: (res) => {
							try {
								// 解析 res.data
								const response = JSON.parse(res.data);
								if (response.code === 200) {
									// 赋值给 this.dutyInfo.imageUrl
									this.dutyInfo.image = response.data;
									// console.log("上传成功，图片地址：", this.dutyInfo.imageUrl);
									resolve(response.data); // 返回图片地址
								} else {
									console.error("上传失败，返回信息：", response.msg);
									reject(response.msg);
								}
							} catch (error) {
								console.error("解析响应数据失败：", error);
								reject(error);
							}
						},
						fail: (error) => {
							console.error("文件上传失败：", error);
							reject(error);
						}
					});
				});
			},
			submitAll() {
				if (isLogin()) {
					console.log(this.dutyInfo);
					dutyAward(this.dutyInfo);
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
			editDutyInfo() {
				uni.navigateTo({
					url: '/pages/edit/dutyInfoEdit',
					success: (res) => {
						// 通过 eventChannel 向被打开页面传送数据
						res.eventChannel.emit('updateDutyInfo', {
							data: this.dutyInfo
						});

						// 接收下级页面返回的数据
						res.eventChannel.on('acceptDutyInfo', (data) => {
							// console.log("接收到下级页面返回的数据：", data);
							this.dutyInfo = {
								...data.data // 注意解构 data.data
							};
						});
					},
				});
			},
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

	.section2 {
		margin-top: 30px;
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

	.beizhu {
		color: #00C853;
		font-size: 10px;
		/* margin-top: 5px; */
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
	
	.upload-image {
		padding: 10px;
	}
	
	.duty-text {
		font-size: 14px;
	}
</style>