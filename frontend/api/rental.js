import request from "@/utils/request";

// 获取资源列表
export function getResources(type) {
	return request({
		url: '/consumer/list',
		method: 'get',
		params:{
			type
		}
	})
}
// 获取团队租用列表
export function getRental(teamId) {
	return request({
		url: '/consumer/rentList',
		method: 'get',
		params:{
			teamId
		}
	})
}
// 租用资源
export function rent(rentalrequest) {
	return request({
		url: '/consumer/rent',
		method: 'post',
		data: rentalrequest
	})
}
// 归还资源
export function returnDevice(returnRequest) {
	return request({
		url: '/consumer/return',
		method: 'post',
		data: returnRequest
	})
}