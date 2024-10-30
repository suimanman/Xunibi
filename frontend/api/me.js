import request from "@/utils/request";

// 登录方法
export function login(userInfo) {
	return request({
		url: '/user/login',
		header: {
			isToken: false
		},
		method: 'post',
		data: userInfo
	})
}

// 注册方法
export function register(data) {
	return request({
		url: '/user/register',
		header: {
			isToken: false
		},
		method: 'post',
		data: data
	})
}

// 退出方法
export function logout() {
	return request({
		url: '/user/logout',
		method: 'get'
	})
}