<template>
	<view class="container">
		<u-tabs :list="list1" @click="click"></u-tabs>

		<view v-if="nowData && nowData.length" class="device-list">
			<view class="device-item" v-for="(device, index) in nowData" :key="index">
				<!-- 左侧图片区域 -->
				<view class="device-image">
					<u-image :src="device.imageUrl" width="80px" height="80px" class="image"></u-image>
					<view class="device-name">{{ device.type }}</view>
				</view>

				<!-- 右侧信息区域 -->
				<view class="device-info">
					<template v-if="device.rentalDate && device.returnDate">
						<view class="device-detail">
							<view class="label">租用:</view>
							<view class="value">{{ device.rentalDate }}</view>
						</view>
						<view class="device-detail">
							<view class="label">归还:</view>
							<view class="value">{{ device.returnDate }}</view>
						</view>
					</template>
					<template v-else>
						<view class="device-detail">
							<view class="label">消耗:</view>
							<view class="value">{{ device.coinConsumption }} 虚拟币</view>
						</view>
						<view class="device-detail">
							<view class="label">状态:</view>
							<view class="value">
								<text :class="device.isAvailable ? 'available' : 'unavailable'">
									{{ device.isAvailable ? '可用' : '不可用' }}
								</text>
							</view>
						</view>
						<view class="device-detail" v-if="!device.isAvailable">
							<view class="label">占用:</view>
							<view class="value">{{ device.rentTeamName }}</view>
						</view>
						<view class="device-detail" v-if="device.isAvailable">
							<view class="label">租期:</view>
							<u-number-box v-model="rentalDays[index]" button-size="27" >
								<view slot="minus" class="minus">
									<u-icon name="minus" size="12"></u-icon>
								</view>
								<text slot="input" style="width: 30px;text-align: center;" class="input">
									{{ rentalDays[index] }} 天
								</text>
								<view slot="plus" class="plus">
									<u-icon name="plus" color="#FFFFFF" size="12"></u-icon>
								</view>
							</u-number-box>
						</view>
					</template>
				</view>

				<!-- 租用/归还按钮 -->
				<view class="device-rent">
					<button v-if="device.rentalDate && device.returnDate" class="rent-button"
						@click="returnDevice(device.type)">
						归 还
					</button>
					<button v-else class="rent-button" :class="{ 'disabled': !device.isAvailable }"
						:disabled="!device.isAvailable" @click="rent(device.type, rentalDays[index])">
						{{ device.isAvailable ? '租 用' : '被租用' }}
					</button>
				</view>
			</view>
		</view>
		<view v-else class="su-empty">
			<view class="su-empty__image">
				<image src="../../static/img/暂无数据.png" mode="aspectFit"></image>
			</view>
			<view class="su-empty__text">
				暂无数据
			</view>
			<slot></slot>
		</view>
	</view>
</template>

<script>
	import {
		getResources,
		getRental,
		rent,
		returnDevice,
	} from '../../api/rental';
	import {
		isLogin
	} from '@/api/me';

	export default {
		data() {
			return {
				currentItem: null,
				rentalDays: [], // 新增用于记录每个设备的租期
				rentalrequest: {
					type1: '',
					type2: '',
					teamId: ''
				},
				list1: [{
						name: '工位'
					},
					{
						name: '固定设备'
					},
					{
						name: '摄像设备'
					},
					{
						name: '场地'
					},
					{
						name: '已租用'
					}
				],
				nowData: []
			};
		},
		created() {
			this.loginHandle();
			if (isLogin()) {
				this.click(this.list1[0]);
			} else {
				console.log("未登录");
			}
		},
		methods: {
			async loginHandle() {
				const loginResult = await isLogin();
				if (!isLogin()) {
					uni.navigateTo({
						url: '/pages/login/login'
					});
				} else {
					this.rentalrequest.teamId = loginResult.data.data.teamId;
				}
			},
			async click(item) {
				this.currentItem = item;
				try {
					this.rentalrequest.type1 = item.name;
					const response = item.name === "已租用" ? await getRental(this.rentalrequest.teamId) :
						await getResources(item.name);
					this.nowData = response.data.data;

					// 设置每个设备的默认租期
					this.rentalDays = this.nowData.map(() => 1);
				} catch (error) {
					this.nowData = null;
					console.error('获取数据失败', error);
				}
			},
			async rent(type, days) {
				try {
					this.rentalrequest.type2 = type;
					this.rentalrequest.rentalDays = days; // 使用各设备对应的租期
					const result = await rent(this.rentalrequest);
					if (result.data.code === 200) {
						uni.showToast({
							title: "租用成功！",
							icon: "success",
							duration: 2000
						});
						this.click(this.currentItem || this.list1[0]);
					} else {
						uni.showToast({
							title: result.data.msg || "无法租用！",
							icon: "none"
						});
					}
				} catch (error) {
					uni.showToast({
						title: "请求异常，请检查网络连接",
						icon: "none"
					});
					console.error('获取数据失败', error);
				}
			},
			async returnDevice(type) {
				try {
					this.rentalrequest.type2 = type;
					const result = await returnDevice(this.rentalrequest);
					if (result.data.code === 200) {
						uni.showToast({
							title: "归还成功！",
							icon: "success",
							duration: 2000
						});
						this.click(this.currentItem || this.list1[4]);
					} else {
						uni.showToast({
							title: result.data.msg || "无法归还！",
							icon: "none"
						});
					}
				} catch (error) {
					uni.showToast({
						title: "请求异常，请检查网络连接",
						icon: "none"
					});
					console.error('归还失败', error);
				}
			}
		}
	};
</script>

<style scoped lang="scss">
	.container {
		padding: 10px;
	}

	.device-list {
		display: flex;
		flex-direction: column;
		margin-top: 10px;
	}

	.device-item {
		display: flex;
		justify-content: space-between;
		margin-bottom: 13px;
		padding: 15px;
		border-radius: 8px;
		background-color: #f9f9f9;
		box-shadow: 1px 2px 2px rgba(0, 0, 0, 0.1);
	}

	.device-image {
		flex: 3;
		display: flex;
		flex-direction: column;
		align-items: center;
		text-align: center;
	}

	.device-info {
		flex: 4;
		display: flex;
		flex-direction: column;
		justify-content: center;
		font-size: 14px;
		color: #333;
	}

	.device-detail {
		display: flex;
		margin-bottom: 5px;
	}

	.label {
		width: 40px;
		color: #666;
		font-weight: bold;
	}

	.value {
		flex: 1;
		color: #333;
	}

	.device-name {
		font-size: 14px;
		margin-top: 5px;
	}

	.device-rent {
		flex: 2;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.image {
		border-radius: 8px;
		margin-bottom: 5px;
		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	}

	.available {
		color: green;
		font-weight: bold;
	}

	.unavailable {
		color: red;
		font-weight: bold;
	}

	.rent-button {
		width: 100%;
		height: 35px;
		padding: 5px 10px;
		border-radius: 8px;
		background-color: #5677fc;
		color: white;
		border: none;
		text-align: center;
		display: flex;
		align-items: center;
		justify-content: center;
		// font-weight: bold;
		font-size: 14px;
		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	}

	.rent-button.disabled {
		background-color: #ccc;
		color: #666;
		cursor: not-allowed;
	}

	.no-data {
		text-align: center;
		color: #888;
		margin-top: 20px;
		font-size: 16px;
	}

	.minus {
		width: 22px;
		height: 22px;
		border-width: 1px;
		border-color: #E6E6E6;
		border-style: solid;
		border-top-left-radius: 100px;
		border-top-right-radius: 100px;
		border-bottom-left-radius: 100px;
		border-bottom-right-radius: 100px;
		@include flex;
		justify-content: center;
		align-items: center;
	}

	.input {
		padding: 0 10px;
	}

	.plus {
		width: 22px;
		height: 22px;
		background-color: #5677fc;
		border-radius: 50%;
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		justify-content: center;
		align-items: center;
	}

	.img-b {
		position: fixed;
		width: 50%;
		bottom: 0;
		left: -50rpx;
		z-index: -1;
	}

	.su-empty {
		padding: 60upx;

		&__image {
			display: flex;
			justify-content: center;
			align-items: center;

			image {
				width: 300upx;
				height: 230upx;
				display: block;
			}
		}

		&__text {
			font-size: 28upx;
			color: #999;
			display: flex;
			justify-content: center;
			align-items: center;
			margin-top: 20upx;
		}
	}
</style>