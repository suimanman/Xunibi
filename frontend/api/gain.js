import request from "@/utils/request";

export function achievementAward(description) {
	return request({
		url: '/gain/achievementSubmit',
		header: {
			isToken: true
		},
		method: 'post',
		data: description
	})
}
export function dutyAward(dutyInfo) {
	return request({
		url: '/gain/dutySubmit',
		header: {
			isToken: true
		},
		method: 'post',
		data: dutyInfo
	})
}