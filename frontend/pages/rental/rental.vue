<template>
    <u-tabs :list="list1" @click="click"></u-tabs>
</template>

<script>
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
                }]
			}
		},
		methods: {
            click(item) {
                console.log('item', item);
            }
		}
	}
</script>
