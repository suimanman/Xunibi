import request from "@/utils/request";

//获取公告
export function getNotice(){
	return request({
		url: '/manage/notice',
		method: 'get',
	})
}