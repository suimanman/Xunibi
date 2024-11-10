<template>
	<u-tabs :list="list1" @click="click"></u-tabs>
</template>

<script>
	import {
		getResources
	} from '@/api/rental';
	export default {
		data() {
			return {
				list1: [{
					name: '工位',
				}, {
					name: '3D打印设备',
				}, {
					name: '激光切割机'
				}, {
					name: '单反相机'
				}, {
					name: '摄像机'
				}, {
					name: '场地'
				}],
				nowData: null
			}
		},
		methods: {
			async click(item) {
				// 根据标签名称从后端获取对应数据
				try {
					const response = await this.getResources(item.name);
					this.nowData = response.data; // 假设后端返回的数据保存在 response.data 中
				} catch (error) {
					console.error('获取数据失败', error);
				}
			},
		}
	}
</script>