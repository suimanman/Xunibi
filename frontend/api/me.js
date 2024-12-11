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
//获取用户信息
export function getUserInfo() {
	return request({
		url: '/user/getUserInfo',
		header: {
			isToken: true
		},
		method: 'get'
	})
}
//验证是否登录
export function isLogin() {
	return request({
		url: '/user/isLogin',
		header: {
			isToken: true
		},
		method: 'get'
	})
}
// 退出方法
export function logout() {
	return request({
		url: '/user/logout',
		method: 'get'
	})
}
//修改密码
export function editPassword(data) {
	return request({
		url: '/user/editPassword',
		method: 'put',
		data: {
			oldPassword: data.oldPassword,
			newPassword: data.newPassword
		}
	})
}
//保存其他信息
export function editElseInfo(data) {
	return request({
		url: '/user/editInfo',
		method: 'post',
		data: data
	})
}