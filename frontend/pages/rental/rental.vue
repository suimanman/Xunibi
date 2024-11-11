<template>
	<view class="container">
		<u-tabs :list="list1" @click="click" ></u-tabs>

		<view v-if="nowData && nowData.length" class="device-list">
			<view class="device-item" v-for="(device, index) in nowData" :key="index">
				<!-- 左侧图片区域 -->
				<view class="device-image">
					<u-image :src="device.imageUrl" width="80px" height="80px" class="image"></u-image>
					<view class="device-name">{{ device.type }}</view>
				</view>

				<!-- 右侧信息区域 -->
				<view class="device-info">
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
				</view>

				<!-- 租用按钮 -->
				<view class="device-rent">
					<button class="rent-button" :class="{ 'disabled': !device.isAvailable }"
						:disabled="!device.isAvailable">
						{{ device.isAvailable ? '租用' : '被租用' }}
					</button>
				</view>
			</view>
		</view>
		<view v-else class="no-data">暂无数据</view>
	</view>
</template>

<script>
	import { getResources } from '../../api/rental';
	export default {
		data() {
			return {
				list1: [{
						name: '工位'
					},
					{
						name: '固定设备'
					},
					{
						name: '摄像'
					},
					{
						name: '场地'
					}
				],
				nowData:[]
			};
		},
		created() {
		        // 自动加载第一个选项
		        this.click(this.list1[0]);
		    },
		methods: {
			async click(item) {
				try {
					// 示例请求方法，用于获取设备列表数据
					const response = await getResources(item.name);
					console.log("资源列表："+response.data.data);
					this.nowData = response.data.data;
				} catch (error) {
					console.error('获取数据失败', error);
				}
			}
		}
	};
</script>

<style scoped>
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
		border: 1px solid #e0e0e0;
		border-radius: 10px;
		background-color: #f9f9f9;
		box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
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
		border-radius: 12px;
		background-color: #5677fc;
		color: white;
		border: none;
		text-align: center;
		display: flex;
		align-items: center;
		justify-content: center;
		font-weight: bold;
		font-size: 14px;
		box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
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
</style>