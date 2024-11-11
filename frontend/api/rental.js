import request from "@/utils/request";

// 登录方法
export function getResources(type) {
	return request({
		url: '/consumer/list',
		method: 'get',
		params:{
			type
		}
	})
}